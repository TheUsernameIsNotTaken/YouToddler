namespace YouToddler.Models
{
    internal record class YouToddlerContent(
        string id,
        string videoTitle,
        string extension,
        Uri thumbnailUrl,
        int fileSize,
        int tbr,
        string moreInfo,
        YouToddlerAudioFormat[] audioFormats,
        YouToddlerVideoFormat[] videoFormats);
}
