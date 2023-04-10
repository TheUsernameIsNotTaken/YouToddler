using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Models;

namespace YouToddler.Artifactory
{
    public interface IYouToddlerArtifactory
    {
        public void CreateArtifact();
        public void UploadArtifact();
    }
}
