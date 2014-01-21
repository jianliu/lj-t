
import li.lucene.le.HelloLucene;
import org.junit.Before;
import org.junit.Test;

public class TestHelloLucene
{
    private HelloLucene lucene;

    @Before
    public void setUp() throws Exception
    {
        lucene = new HelloLucene();
    }

    @Test
    public void testIndex()
    {
        lucene.index();
    }

    @Test
    public void testSearch()
    {
        lucene.search();
    }
}