using CommandLine;

namespace YouToddlerCLI.Options
{
    [Verb("download", false, new[] { "d", "dl" }, HelpText = "Downloads the input YouTube content.")]
    public class DownloadOptions
    {
        [Option('t', "target", HelpText = "The URI that points to the input YouTube content.", Required = true)]
        public string? VideoUriLink { get; set; }

        [Option('v', "videoFormatId", HelpText = "The id of the video media track of the input content you want to download.")]
        public int VideoFormatId { get; set; }
        [Option('a', "audioFormatId", HelpText = "The id of the audio media track of the input content you want to download.")]
        public int AudioFormatId { get; set; }
    }
}
