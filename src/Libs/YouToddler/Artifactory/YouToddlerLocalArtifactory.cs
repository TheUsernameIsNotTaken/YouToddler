using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Configuration;
using YouToddler.Models;

namespace YouToddler.Artifactory
{
    internal class YouToddlerLocalArtifactory : IYouToddlerArtifactory
    {
        private YouToddlerArtifactoryConfiguration _configuration;
        public YouToddlerLocalArtifactory() { }

        Task IYouToddlerArtifactory.CreateArtifact(YouToddlerContent youToddlerContent)
        {
            throw new NotImplementedException();
        }

        Task<bool> IYouToddlerArtifactory.UploadArtifacts()
        {
            throw new NotImplementedException();
        }
    }
}
