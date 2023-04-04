using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Models;

namespace YouToddler.Artifactory
{
    internal interface IYouToddlerArtifactory
    {
        internal Task CreateArtifact(YouToddlerContent youToddlerContent);
        internal Task<bool> UploadArtifacts();
    }
}
