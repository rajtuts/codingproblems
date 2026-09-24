package sorting;


    /* Kth largest element */
// URL: https://leetcode.com/problems/kth-largest-element-in-an-array/
    class FindKthLargestLeetCode215 {
        public int findKthLargest(int[] nums, int k) {
            return quickSelect(nums, 0, nums.length - 1, nums.length - k);
        }

        private static int quickSelect(int[] nums, int low, int high, int k) {
            int pi = partition(nums, low, high);
            if (pi == k) {
                return nums[pi];
            } else if (k > pi) {
                return quickSelect(nums, pi + 1, high, k);
            } else {
                return quickSelect(nums, low, pi - 1, k);
            }
        }

        private static int partition(int[] nums, int low, int high) {
            int pivot = nums[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (pivot > nums[j]) {
                    i++;
                    swap(nums, i, j);
                }
            }
            swap(nums, i + 1, high);
            return (i + 1);
        }

        private static void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    } // TC: O(n), SC: O(log n)



    /* Kth smalleset element */
// URL: https://practice.geeksforgeeks.org/problems/kth-smallest-element5635/1
    class FindKthSmallestLeetCode215{
        public static int kthSmallest(int[] arr, int l, int r, int k)
        {
            return quickSelect(arr, l, r, k - 1); // nums.length - k: largest element
        }
        private static int quickSelect(int[] nums, int low, int high, int k) {
            int pi = partition(nums, low, high);
            if (pi == k) {
                return nums[pi];
            } else if (k > pi) {
                return quickSelect(nums, pi + 1, high, k);
            } else {
                return quickSelect(nums, low, pi - 1, k);
            }
        }

        private static int partition(int[] nums, int low, int high) {
            int pivot = nums[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (pivot > nums[j]) {
                    i++;
                    swap(nums, i, j);
                }
            }
            swap(nums, i + 1, high);
            return (i + 1);
        }

        private static void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }


