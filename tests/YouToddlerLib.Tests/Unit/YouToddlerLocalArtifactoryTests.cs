using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Artifactory;
using YouToddler.Configuration;

namespace YouToddlerLib.Tests.Unit
{
    [TestFixture]
    public class YouToddlerLocalArtifactoryTests
    {
        private IConfiguration configuration;

        [OneTimeSetUp] 
        public void SetUp() 
        {
            configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json")
                .Build();
        }

        [Test]
        public void CreateArtifact_ThrowsDirectoryException_WhenStagingDirectoryIsMissing()
        {
            var artifactory = new YouToddlerLocalArtifactory(configuration);

            Assert.That(() => artifactory.CreateArtifact(), Throws.Exception.TypeOf<DirectoryNotFoundException>());
        }
        [Test]
        public void CreateArtifact_ThrowsDirectoryException_WhenStagingDirectoryIsEmpty()
        {

            var artifactory = new YouToddlerLocalArtifactory(configuration);
            Directory.CreateDirectory(artifactory.Configuration.StagingDirectory);

            Assert.That(() => artifactory.CreateArtifact(), Throws.Exception.TypeOf<DirectoryNotFoundException>());

            Directory.Delete(artifactory.Configuration.StagingDirectory);
        }

        [Test]
        public void CreateArtifact_ArchivesDownloadedContent_WhenContentAvailable()
        {

            var artifactory = new YouToddlerLocalArtifactory(configuration);
            Directory.CreateDirectory(artifactory.Configuration.StagingDirectory);
            File.WriteAllText($"{artifactory.Configuration.StagingDirectory}/bela1.txt", "Béla");
            File.WriteAllText($"{artifactory.Configuration.StagingDirectory}/bela2.txt", "Béla");
            File.WriteAllText($"{artifactory.Configuration.StagingDirectory}/bela3.txt", "Béla");

            artifactory.CreateArtifact();

            Assert.That(new DirectoryInfo(artifactory.Configuration.ArtifactStagingDirectory), Is.Not.Empty);
            Assert.That(new DirectoryInfo(artifactory.Configuration.ArtifactStagingDirectory).GetFiles(), Has.Exactly(1).Items);

            Directory.Delete(artifactory.Configuration.ArtifactStagingDirectory, true);
        }

        [Test]
        public void UploadArtifact_ThrowsFileNotFoundException_WhenArtifactStagingDirectoryIsEmpty()
        {

            var artifactory = new YouToddlerLocalArtifactory(configuration);
            Directory.CreateDirectory(artifactory.Configuration.StagingDirectory);

            Assert.That(() => artifactory.UploadArtifact(), Throws.Exception.TypeOf<FileNotFoundException>());

            Directory.Delete(artifactory.Configuration.StagingDirectory);
        }

        [Test]
        public void UploadArtifact_ArchivesDownloadedContent_WhenContentAvailable()
        {

            var artifactory = new YouToddlerLocalArtifactory(configuration);
            Directory.CreateDirectory(artifactory.Configuration.StagingDirectory);
            File.WriteAllText($"{artifactory.Configuration.StagingDirectory}/bela1.txt", "Béla");
            File.WriteAllText($"{artifactory.Configuration.StagingDirectory}/bela2.txt", "Béla");
            File.WriteAllText($"{artifactory.Configuration.StagingDirectory}/bela3.txt", "Béla");

            artifactory.CreateArtifact();
            artifactory.UploadArtifact();

            Assert.That(new DirectoryInfo(artifactory.Configuration.ArtifactUploadDestination), Is.Not.Empty);
            Assert.That(new DirectoryInfo(artifactory.Configuration.ArtifactUploadDestination).GetFiles(), Has.Exactly(1).Items);

            Directory.Delete(artifactory.Configuration.ArtifactStagingDirectory, true);
            Directory.Delete(artifactory.Configuration.ArtifactUploadDestination, true);
        }
    }
}
