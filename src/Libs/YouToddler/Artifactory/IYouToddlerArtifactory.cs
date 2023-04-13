namespace YouToddler.Artifactory
{
    public interface IYouToddlerArtifactory
    {
        public string CreateArtifact();
        public void UploadArtifact(string artifactFilename);
    }
}
