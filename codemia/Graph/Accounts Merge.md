Accounts Merge
Given a list of accounts where each account contains a name and emails, merge accounts belonging to the same person (accounts that share at least one common email).

Example 1:
Input: {"accounts":[["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]}
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]


Approach: Union-Find

Key Insight: Two accounts belong to the same person if they share any email. Use Union-Find to group all emails belonging to the same person, then collect and sort emails for each group.

Algorithm:

Map each email to an owner (first account index)
For accounts with multiple emails, union all emails
Group emails by their root representative
For each group, sort emails and prepend name
Return merged accounts
Example: accounts = [["John","a@"],["John","b@"],["John","a@","c@"]]

a@ → account 0, b@ → account 1, c@ → account 2
Account 2 has a@ and c@: union(a@, c@)
But a@ was in account 0, so accounts 0 and 2 are same person
Groups: {0: [a@, c@], 1: [b@]}
Result: [["John","a@","c@"],["John","b@"]] ✓
Time Complexity: O(n × α(n) × log(n)) - union-find with sorting Space Complexity: O(n) - email mappings

Time: O(n * k * α(n))
Space: O(n * k)

```
class Solution {
    Map<String, String> parent = new HashMap<>();

    public String[][] accountsMerge(String[][] accounts) {
        Map<String, String> emailToName = new HashMap<>();
        for (String[] account : accounts) {
            String name = account[0];
            for (int i = 1; i < account.length; i++) {
                emailToName.put(account[i], name);
                union(account[1], account[i]);
            }
        }

        Map<String, List<String>> groups = new HashMap<>();
        for (String email : emailToName.keySet()) {
            String root = find(email);
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        List<String[]> result = new ArrayList<>();
        for (List<String> emails : groups.values()) {
            Collections.sort(emails);
            String[] merged = new String[emails.size() + 1];
            merged[0] = emailToName.get(emails.get(0));
            for (int i = 0; i < emails.size(); i++) {
                merged[i + 1] = emails.get(i);
            }
            result.add(merged);
        }
        return result.toArray(new String[0][]);
    }

    private String find(String x) {
        parent.putIfAbsent(x, x);
        if (!parent.get(x).equals(x)) parent.put(x, find(parent.get(x)));
        return parent.get(x);
    }

    private void union(String x, String y) {
        parent.put(find(x), find(y));
    }
}
```
