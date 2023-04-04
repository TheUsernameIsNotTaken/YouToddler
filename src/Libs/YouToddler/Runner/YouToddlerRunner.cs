using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO.Abstractions;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddler.Runner
{
    internal class YouToddlerRunner
    {
        private ProcessStartInfo _toolProcess;
        private IFileSystem _fileSystem;

        public YouToddlerRunner(IFileSystem fileSystem)
        {
            _fileSystem = fileSystem;
        }
    }
}
