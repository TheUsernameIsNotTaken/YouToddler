using Microsoft.Extensions.Configuration;
using Serilog;
using System.IO.Compression;
using YouToddler.Configuration;

namespace YouToddler.Artifactory
{
    public class YouToddlerLocalArtifactory : IYouToddlerArtifactory
    {
        public YouToddlerConfiguration Configuration { get; private set; }
        public YouToddlerLocalArtifactory(IConfiguration configuration)
        {
            Configuration = configuration.GetSection("YouToddlerConfiguration").Get<YouToddlerConfiguration>();
            Directory.CreateDirectory(Configuration.ArtifactStagingDirectory);
            Directory.CreateDirectory(Configuration.ArtifactUploadDestination);
        }

        public void CreateArtifact()
        {
            Log.Debug("Checking existance of the staging directory.");
            if (Directory.Exists(Configuration.StagingDirectory) && Directory.GetFiles(Configuration.StagingDirectory).Length > 0)
            {
                Log.Debug("Staging directory exists! Procceding..");
            }
            else
            {
                Log.Fatal($"ABORTING! Staging directory doesn't exists or it is empty! ::{Configuration.StagingDirectory}::");
                throw new DirectoryNotFoundException(Configuration.StagingDirectory);
            }

            Log.Information("Create archive from the downloaded content");
            ZipFile.CreateFromDirectory(
                Configuration.StagingDirectory,
                Path.Combine(
                    Configuration.ArtifactStagingDirectory,
                    $"ytoddler-download-{((DateTimeOffset)DateTime.Now).ToUnixTimeSeconds()}.zip"));
            Log.Information("Created ZIP artifact.");
            Log.Information("Cleaning up staging directory.");
            foreach (var file in Directory.GetFiles(Configuration.StagingDirectory))
            {
                File.Delete(file);
            }
            Log.Information("Cleaned up staging directory.");
        }

        public void UploadArtifact()
        {
            var artifact = Directory.EnumerateFiles(
                Configuration.ArtifactStagingDirectory,
                "*zip",
                SearchOption.AllDirectories).FirstOrDefault(string.Empty);
            if (!string.IsNullOrEmpty(artifact))
            {
                Log.Information("Uploading artifact to local artifactory repository.");
                File.Copy(
                    artifact,
                    Path.Combine(Configuration.ArtifactUploadDestination, Path.GetFileName(artifact)));
                Log.Information("Uploaded artifact to local artifactory repository.");
            }
            else
            {
                Log.Fatal($"Couldn't find any zip artifacts to upload in the artifact staging folder: {Configuration.ArtifactStagingDirectory}");
                throw new FileNotFoundException(artifact);
            }
        }
    }
}
