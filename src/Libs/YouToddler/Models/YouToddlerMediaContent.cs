namespace YouToddler.Models
{
    public record class YouToddlerMediaContent(
        int id,
        string videoTitle,
        string extension,
        Uri thumbnailUrl,
        int fileSize,
        int tbr,
        string moreInfo,
        YouToddlerAudioFormat audioFormat,
        YouToddlerVideoFormat videoFormat);
}
