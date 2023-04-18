namespace YouToddler.Configuration
{
    public class YouToddlerConfiguration
    {
        public string StagingDirectory { get; set; }
        public string ArtifactStagingDirectory { get; set; }
        public string ArtifactUploadDestination { get; set; }
        public YouToddlerConfiguration()
        {
            StagingDirectory = string.Empty;
            ArtifactStagingDirectory = string.Empty;
            ArtifactUploadDestination = string.Empty;
        }
    }
}
