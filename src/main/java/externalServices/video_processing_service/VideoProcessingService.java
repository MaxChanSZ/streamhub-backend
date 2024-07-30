package externalServices.video_processing_service;
import com.github.kokorin.jaffree.ffmpeg.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VideoProcessingService implements IVideoProcessingService{
    @Override
    public void mp4ToHls(String mp4) {
        Path input = Paths.get(mp4);
        Path outputDir = Paths.get("output");

        // Ensure output directory exists
        outputDir.toFile().mkdirs();

        // Transcode to 1080p
        transcodeToHls(input, outputDir.resolve("1080p.m3u8"), 1080, 5000);

        // Transcode to 720p
        transcodeToHls(input, outputDir.resolve("720p.m3u8"), 720, 3000);

        // Transcode to 480p
        transcodeToHls(input, outputDir.resolve("480p.m3u8"), 480, 1500);

        // Create the master playlist
        createMasterPlaylist(outputDir, outputDir.resolve("master.m3u8"));
    }

    private void transcodeToHls(Path input, Path output, int height, int bitrate) {
        FFmpeg.atPath()
                .addInput(UrlInput.fromPath(input))
                .addOutput(
                        UrlOutput.toPath(output)
                                .setFormat("hls")
                                .addArguments("-vf", "scale=-2:" + height)
                                .addArguments("-c:v", "libx264")
                                .addArguments("-b:v", bitrate + "k")
                                .addArguments("-c:a", "aac")
                                .addArguments("-ar", "48000")
                                .addArguments("-b:a", "128k")
                                .addArguments("-hls_time", "10")
                                .addArguments("-hls_playlist_type", "vod")
                )
                .execute();
    }

    private void createMasterPlaylist(Path outputDir, Path masterPlaylist) {
        String playlistContent = "#EXTM3U\n" +
                "#EXT-X-STREAM-INF:BANDWIDTH=5000000,RESOLUTION=1920x1080\n" +
                "1080p.m3u8\n" +
                "#EXT-X-STREAM-INF:BANDWIDTH=3000000,RESOLUTION=1280x720\n" +
                "720p.m3u8\n" +
                "#EXT-X-STREAM-INF:BANDWIDTH=1500000,RESOLUTION=854x480\n" +
                "480p.m3u8";

        try {
            java.nio.file.Files.write(masterPlaylist, playlistContent.getBytes());
            System.out.println("Master playlist created: " + masterPlaylist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
