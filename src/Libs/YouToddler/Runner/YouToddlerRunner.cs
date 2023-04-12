using Serilog;
using System.Diagnostics;

namespace YouToddler.Runner
{
    public static class YouToddlerRunner
    {
        private static ProcessStartInfo _toolProcess;
        private static string _ytDlpBinaryName = OperatingSystem.IsWindows() ? "yt-dlp.exe" : "yt-dlp";
        private static string _ffmpegBinaryName = OperatingSystem.IsWindows() ? "ffmpeg.exe" : "ffmpeg";

        static YouToddlerRunner()
        {
            _toolProcess = new ProcessStartInfo();
            _toolProcess.UseShellExecute = false;
            _toolProcess.RedirectStandardOutput = true;
            _toolProcess.RedirectStandardError = true;
            _toolProcess.CreateNoWindow = true;

            bool dependenciesInstalled = true;
            dependenciesInstalled &= IsDependencyInstalledOnPath(_ytDlpBinaryName, "--version");
            dependenciesInstalled &= IsDependencyInstalledOnPath(_ffmpegBinaryName, "-h");

            if (!dependenciesInstalled) 
            {
                throw new InvalidOperationException($"YT-DLP and/or its dependencies are not installed. They cannot be found on PATH.");
            }

            _toolProcess.FileName = _ytDlpBinaryName;
        }

        public static (int exitCode, string output) ExecuteYtDlp(string arguments)
        {
            string output = "";
            _toolProcess.Arguments = arguments;
            using (Process p = Process.Start(_toolProcess)!)
            {
                p.WaitForExit(TimeSpan.FromHours(1));
                if (p.ExitCode != 0) 
                {
                    using (StreamReader sr = p.StandardError)
                    {
                        output = sr.ReadToEnd();
                        using (StreamReader sro = p.StandardOutput)
                        {
                            output += sro.ReadToEnd();
                            return (p.ExitCode, output);
                        }
                    }
                }
                else
                {
                    using (StreamReader sr = p.StandardOutput)
                    {
                        output = sr.ReadToEnd();
                        return (p.ExitCode, output);
                    }
                }
            }
        }

        private static bool IsDependencyInstalledOnPath(string binaryName, string arguments)
        {
            string output = "";
            _toolProcess.FileName = binaryName;
            _toolProcess.Arguments = arguments;
            using (Process? p = Process.Start(_toolProcess))
            {
                if (p is null)
                {
                    Log.Fatal($"{binaryName} (and/or its dependencies) is not installed on PATH! Please install {binaryName} and its dependencies on the host system.");
                    return false;
                }
                using (StreamReader sr = p.StandardOutput)
                {
                    output = sr.ReadToEnd();

                    return !string.IsNullOrEmpty(output);
                }
            }
        }
    }
}
