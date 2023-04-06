using NUnit.Framework;
using Serilog;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.History;

namespace YouToddlerLib.Tests.Unit
{
    [TestFixture]
    public class YouToddlerHistorianTests
    {
        [Test]
        [Ignore("meh")]
        public void AggregateLogs_NotImplemented_ThrowsNotImplementedExecption()
        {
            Assert.Throws<NotImplementedException>(() => YouToddlerHistorian.AggregateLogs(new DirectoryInfo(".")));
        }
    }
}
