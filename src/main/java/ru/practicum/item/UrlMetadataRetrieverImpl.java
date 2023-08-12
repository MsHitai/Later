package ru.practicum.item;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import ru.practicum.exceptions.ItemRetrieverException;



import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;


@Slf4j
@Service
public class UrlMetadataRetrieverImpl implements UrlMetadataRetriever {

    private final HttpClient client;

    public UrlMetadataRetrieverImpl(@Value("${url-metadata-retriever.read_timeout-sec:120}") int readTimeout) {

        this.client =  HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(readTimeout))
                .build();
    }

    @Override
    public UrlMetadata retrieve(String urlString) {
        final URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new ItemRetrieverException("The URL is malformed: " + urlString, e);
        }

        HttpResponse<Void> resp = connect(uri, "HEAD", HttpResponse.BodyHandlers.discarding());

        assert resp != null;
        String contentType = resp.headers()
                .firstValue(HttpHeaders.CONTENT_TYPE)
                .orElse("*");

        MediaType mediaType = MediaType.parseMediaType(contentType);

        final UrlMetadataImpl result;

        if (mediaType.isCompatibleWith(MimeType.valueOf("text/*"))) {
            result = handleText(resp.uri());
        } else if (mediaType.isCompatibleWith(MimeType.valueOf("image/*"))) {
            result = handleImage(resp.uri());
        } else if (mediaType.isCompatibleWith(MimeType.valueOf("video/*"))) {
            result = handleVideo(resp.uri());
        } else {
            throw new ItemRetrieverException("The content type [" + mediaType
                    + "] at the specified URL is not supported.");
        }

        return result.toBuilder()
                .normalUrl(urlString)
                .resolvedUrl(resp.uri().toString())
                .mimeType(mediaType.getType())
                .dateResolved(Instant.now())
                .build();
    }

    private <T> HttpResponse<T> connect(URI url,
                                        String method,
                                        HttpResponse.BodyHandler<T> responseBodyHandler) {

        return null;
    }

    private UrlMetadataImpl handleText(URI url) {
        HttpResponse<String> resp = connect(url, "GET", HttpResponse.BodyHandlers.ofString());

        assert resp != null;
        Document doc = Jsoup.parse(resp.body());

        Elements imgElements = doc.getElementsByTag("img");
        Elements videoElements = doc.getElementsByTag("video");

        return UrlMetadataImpl.builder()
                .title(doc.title())
                .hasImage(!imgElements.isEmpty())
                .hasVideo(!videoElements.isEmpty())
                .build();
    }

    private UrlMetadataImpl handleVideo(URI url) {
        String name = new File(url).getName();
        return UrlMetadataImpl.builder()
                .title(name)
                .hasVideo(true)
                .build();
    }

    private UrlMetadataImpl handleImage(URI url) {
        String name = new File(url).getName();
        return UrlMetadataImpl.builder()
                .title(name)
                .hasImage(true)
                .build();
    }

    @lombok.Value
    @Builder(toBuilder = true)
    static class UrlMetadataImpl implements UrlMetadata {
        String normalUrl;
        String resolvedUrl;
        String mimeType;
        String title;
        boolean hasImage;
        boolean hasVideo;
        Instant dateResolved;
    }
}


