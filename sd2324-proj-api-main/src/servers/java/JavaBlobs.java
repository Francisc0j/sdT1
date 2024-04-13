package servers.java;

import tukano.api.java.Blobs;
import tukano.api.java.Result;

import java.util.function.Consumer;

public class JavaBlobs implements Blobs {
    @Override
    public Result<Void> upload(String blobId, byte[] bytes) {
        return null;
    }

    @Override
    public Result<byte[]> download(String blobId) {
        return null;
    }

    @Override
    public Result<Void> downloadToSink(String blobId, Consumer<byte[]> sink) {
        return Blobs.super.downloadToSink(blobId, sink);
    }
}
