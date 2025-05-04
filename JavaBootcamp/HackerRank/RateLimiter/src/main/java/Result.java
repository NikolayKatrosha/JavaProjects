import java.util.*;

class Result {

    public static List<String> getRequestStatus(List<String> requests) {
        Map<String, DomainData> uniqueDomains = new HashMap<>();

        for (int i = 0; i < requests.size(); i++) {
            String domain = requests.get(i);
            uniqueDomains.putIfAbsent(domain, new DomainData());
            uniqueDomains.get(domain).positions.add(i);
        }

        for (String domain : uniqueDomains.keySet()) {
            DomainData data = uniqueDomains.get(domain);
            List<Integer> pos = data.positions;

            for (int i = 0; i < pos.size(); i++) {
                int t1 = pos.get(i);


                if (i < 2) {
                    data.flags.add(true);
                    continue;
                }

                int t2 = pos.get(i - 1);
                int t3 = pos.get(i - 2);

                if (!(t1 - t2 > 5 && t1 - t3 > 5)) {
                    data.flags.add(false);
                    continue;
                }


                if (i < 5) {
                    data.flags.add(true);
                    continue;
                }

                boolean passed30 = true;

                for (int j = 1; j <= 5; j++) {
                    int prevTime = pos.get(i - j);
                    if (data.flags.get(i - j) && t1 - prevTime <= 29) {
                        passed30 = false;
                        break;
                    }
                }

                if (!passed30) {
                    data.flags.add(false);
                } else {
                    data.flags.add(true);
                }
            }
        }


        List<String> result = new ArrayList<>();

        for (String domain : requests) {
            DomainData data = uniqueDomains.get(domain);
            boolean accepted = data.flags.remove(0);

            if (accepted) {
                result.add("200 OK");
            } else {
                result.add("429 Too Many Requests");
            }
        }

        return result;
    }
}

class DomainData {
    List<Integer> positions;
    List<Boolean> flags;

    DomainData() {
        positions = new ArrayList<>();
        flags = new ArrayList<>();
    }
}
