package pl.home;

import java.util.*;

public class Result {

    public static List<String> getRequestStatus(List<String> requests) {
        Map<String, List<DomainRequest>> domainRequestsMap = new HashMap<>();
        Map<String, Integer> requestShift = new HashMap<>();

        for (int i = 0; i < requests.size(); i++) {
            String domain = requests.get(i);
            requestShift.putIfAbsent(domain, i);
            domainRequestsMap.put(domain, new ArrayList<>());

            var domainRequest = new DomainRequest();
            domainRequest.setDomainName(domain);
            domainRequest.setOriginSecond(i);
            domainRequest.setStatus("200 OK");
            domainRequest.setShift(requestShift.get(domain));
            domainRequestsMap.getOrDefault(domain, new ArrayList<>()).add(domainRequest);
        }

        for (Map.Entry<String, List<DomainRequest>> entry : domainRequestsMap.entrySet()) {
            var domainRequests = entry.getValue();
            var finalSecond = domainRequests.getLast().getOriginSecond() - domainRequests.getLast().getShift();
            for (int i = 0; i < finalSecond; i++) {
                if (i > 0 && i % 5 == 0) {
                    var start = i - 5;
                    var end = i;
                    var domainRequestsBatch = domainRequests.stream()
                            .filter(domainRequest -> domainRequest.getSecond() >= start && domainRequest.getSecond() <= end)
                            .toList();
                    if (domainRequestsBatch.size() > 2) {
                        for (int j = 2; j < domainRequestsBatch.size(); j++) {
                            domainRequestsBatch.get(j).setStatus("429 Too Many Requests");
                        }
                    }
                }

                if (i > 0 && i % 30 == 0) {
                    var start = i - 30;
                    var end = i;
                    var domainRequestsBatch = domainRequests.stream()
                            .filter(domainRequest -> domainRequest.getSecond() >= start && domainRequest.getSecond() <= end)
                            .toList();
                    if (domainRequestsBatch.size() > 5) {
                        for (int j = 5; j < domainRequestsBatch.size(); j++) {
                            domainRequestsBatch.get(j).setStatus("429 Too Many Requests");
                        }
                    }
                }
            }
        }

        var finalRequests = domainRequestsMap.entrySet()
                .stream()
                .flatMap(m -> m.getValue().stream())
                .sorted(Comparator.comparing(DomainRequest::getOriginSecond))
                .toList();
        return finalRequests.stream().map(DomainRequest::getStatus)
                .peek(status -> System.out.printf("Status: %s%n \n", status))
                .toList();
    }
}
