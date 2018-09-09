package vio;

import com.teamdev.jxcapture.Codec;
import com.teamdev.jxcapture.CompressionQuality;
import com.teamdev.jxcapture.EncodingParameters;
import com.teamdev.jxcapture.VideoCapture;
import com.teamdev.jxcapture.audio.AudioCodec;
import com.teamdev.jxcapture.audio.AudioEncodingParameters;
import com.teamdev.jxcapture.audio.AudioSource;
import com.teamdev.jxcapture.video.VideoFormat;
import java.awt.Rectangle;

import java.io.File;
import java.util.List;
public class VideoRecording {
    VideoCapture videoCapture = VideoCapture.create(VideoFormat.WMV);
    String videoName;
    public EncodingParameters videoInitialisation(){
        
        videoCapture.setCaptureArea(new Rectangle(0,30,490,380));
        
        List<Codec> videoCodecs = videoCapture.getVideoCodecs();
        for (Codec vid : videoCodecs ) {
            System.out.println("audioSource = " + vid);
        }
        Codec videoCodec = videoCodecs.get(0);
        System.out.println("videoCodec = " + videoCodec);
        videoName="WebCamera"+(int)System.currentTimeMillis()/10+".wmv";
        
        EncodingParameters encodingParameters = new EncodingParameters(new File(videoName));
        encodingParameters.setBitrate(1000000);
        encodingParameters.setFramerate(20);
        encodingParameters.setKeyFrameInterval(1);
        encodingParameters.setCodec(videoCodec);
        encodingParameters.setCompressionQuality(CompressionQuality.HIGH);
        System.out.println("Available audio recording sources:");
        List<AudioSource> audioSources = AudioSource.getAvailable();
        for (AudioSource audioSource : audioSources) {
            System.out.println("audioSource  = " + audioSource);
        }
        if (audioSources.isEmpty()) {
            System.err.println("No audio sources available");
        } else {
            AudioSource audioSource = audioSources.get(0);
            System.out.println("Selected audio source = " + audioSource);
            videoCapture.setAudioSource(audioSource);
            
            List<AudioCodec> audioCodecs = videoCapture.getAudioCodecs();
            if (audioSources.isEmpty()) {
                System.err.println("No audio codecs available");
            } else {
                System.out.println("Available audio codecs:");
                for (AudioCodec audioCodec : audioCodecs) {
                    System.out.println("audioCodec = " + audioCodec);
                }

                // Enable and configure audio encoding
                AudioEncodingParameters audioEncoding = new AudioEncodingParameters();
                
                AudioCodec audioCodec = audioCodecs.get(0);
                System.out.println("Selected audio codec = " + audioCodec);
                audioEncoding.setCodec(audioCodec);

                encodingParameters.setAudioEncoding(audioEncoding);
            }
        }
        System.out.println("encodingParameters = " + encodingParameters);
//        new Frame1(webCamera).setVisible(true);
        
        return encodingParameters;
    }
    public void startVideo()
    {
        EncodingParameters encodingParameters=videoInitialisation();
        videoCapture.start(encodingParameters);
        
    }
    public String endVideo()
    {
        videoCapture.stop();
        return videoName;
    }
}
