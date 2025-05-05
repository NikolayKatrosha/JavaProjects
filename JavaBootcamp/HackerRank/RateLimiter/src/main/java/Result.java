import java.util.*;

class Result {

    public static List<String> getRequestStatus(List<String> requests) {
        Map<String, DomainData> uniqueDomains = new HashMap<>();

        for (int i = 0; i < requests.size(); i++) {
            String domain = requests.get(i);
            uniqueDomains.putIfAbsent(domain, new DomainData());
            uniqueDomains.get(domain).positions().add(i);
        }

        for (Map.Entry<String, DomainData> entry : uniqueDomains.entrySet()) {
            List<Integer> pos = entry.getValue().positions();
            List<Boolean> flags = entry.getValue().flags();

            for (int i = 0; i < pos.size(); i++) {
                int currentTime = pos.get(i);

                if (i < 2) {
                    flags.add(true);
                    continue;
                }

                int previousTime1 = pos.get(i - 1);
                int previousTime2 = pos.get(i - 2);

                if (!(currentTime - previousTime1 > 5 && currentTime - previousTime2 > 5)) {
                    flags.add(false);
                    continue;
                }

                if (i < 5) {
                    flags.add(true);
                    continue;
                }

                boolean passed30 = true;
                for (int j = 1; j <= 5; j++) {
                    int pastTime = pos.get(i - j);
                    if (flags.get(i - j) && currentTime - pastTime <= 29) {
                        passed30 = false;
                        break;
                    }
                }

                flags.add(passed30);
            }
        }

        List<String> result = new ArrayList<>();
        for (String domain : requests) {
            DomainData data = uniqueDomains.get(domain);
            boolean accepted = data.flags().remove(0);
            result.add(accepted ? "200 OK" : "429 Too Many Requests");
        }

        return result;
    }
}

//  Using a record with mutable fields
record DomainData(List<Integer> positions, List<Boolean> flags) {
    DomainData() {
        this(new ArrayList<>(), new ArrayList<>());
    }
}
