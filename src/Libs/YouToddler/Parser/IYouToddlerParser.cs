namespace YouToddler.Parser
{
    public interface IYouToddlerParser<T> where T : class
    {
        public T ParseMetadata(string ytDlpOutput);
    }
}
