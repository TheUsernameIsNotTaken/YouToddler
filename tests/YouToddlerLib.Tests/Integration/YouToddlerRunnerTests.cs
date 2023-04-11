using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Runner;

namespace YouToddlerLib.Tests.Integration
{
    [TestFixture]
    public class YouToddlerRunnerTests
    {
        [Test]
        public void ExecuteYtDlp_GetYtDlpVersion_ReturnsVersionExitCodeZero()
        {
            (int exitCode, string output) result = YouToddlerRunner.ExecuteYtDlp("--version");
            Assert.That(result.exitCode, Is.EqualTo(0));
            Assert.That(result.output, Is.Not.Empty);
        }
    }
}
