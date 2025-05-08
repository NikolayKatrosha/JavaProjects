import org.junit.jupiter.api.Test;
import pl.home.Result;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {

    @Test
    void testAllAcceptedSimple() {
        List<String> requests = List.of("x.com", "x.com");
        List<String> result = Result.getRequestStatus(requests);

        assertEquals(List.of("200 OK", "200 OK"), result);
    }

    @Test
    void testTooFrequentRequests() {
        List<String> requests = List.of(
                "x.com", "x.com", "x.com", "x.com", "x.com", "x.com"
        );

        List<String> result = Result.getRequestStatus(requests);


        assertTrue(result.stream().anyMatch(r -> r.equals("429 Too Many Requests")));
    }

    @Test
    void testAlternatingDomains() {
        List<String> requests = List.of(
                "a.com", "b.com", "a.com", "b.com", "a.com", "b.com"
        );

        List<String> result = Result.getRequestStatus(requests);


        assertTrue(result.stream().allMatch(r -> r.equals("200 OK")));
    }

    @Test
    void testExactlyFiveRequestsSpaced() {
        List<String> requests = List.of(
                "d.com", "d.com", "d.com", "d.com", "d.com"
        );

        List<String> result = Result.getRequestStatus(requests);


        assertEquals(5, result.size());
    }

    @Test
    void testEmptyInput() {
        List<String> requests = List.of();
        List<String> result = Result.getRequestStatus(requests);

        assertTrue(result.isEmpty());
    }
}
