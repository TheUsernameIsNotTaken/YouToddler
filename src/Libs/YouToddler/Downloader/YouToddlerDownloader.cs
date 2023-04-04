using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Runner;

namespace YouToddler.Downloader
{
    internal class YouToddlerDownloader
    {
        private YouToddlerRunner _runner;

        public YouToddlerDownloader(YouToddlerRunner runner)
        {
            _runner = runner;
        }

        internal bool DownloadContent(string content)
        {
            return false;
        }
        internal bool DownloadContent(string content, YouToddlerDownloaderArguments args)
        {
            return false;
        }
    }
}
