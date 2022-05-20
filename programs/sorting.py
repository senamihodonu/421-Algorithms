
from array import array
import time
import random
import sys

def Usage():
    return "Usage: python3 sorting.py <sort_type> <num_elements> "

def InsertionSort(A):
    """Funtion to sort an array using
    insertion sort"""                   #cost   #times
    n = len(A)                          #c1     n
    key = A[0]                          #c2     n
    j = 2                               #c3     n
    for j in range(1, n):               #c4     n
        key = A[j]                      #c5     n-1
        i = j-1                         #c6     n-1
        while i >= 0 and A[i] > key:    #c7     summation range(2-n) t_j
            A[i+1] = A[i]               #c8     summation range(2-n) (t_j-1)
            i = i-1                     #c9     summation range(2-n) (t_j-1)
        A[i+1] = key                    #c10    n-1

# T(n) = n(c1+c2+c3+c4)+(n-1)(c5+c6+c10)+c7(summation range(2-n) t_j)+ 
# (c8+c9)(summation range(2-n) (t_j-1))
# Best case: Input already sorted
# t_j = 1
# T(n) = n(c1+c2+c3+c4+c5+c6+c7+c10)+(c5+c6+C7+c10)
# --> T(n) = theta(n)
# Worst case: Input reverse sorted
# t_j = j
# --> T(n) = theta(n^2)
# Average case: Input reverse sorted
# t_j = j/2
# --> T(n) = theta(n^2)

array = []
limit = int(sys.argv[2])
for i in range(limit):
    t = random.randint(0, 0)
    array.append(t)

if limit < 100:
    print("Unsorted array")
    print(array)

# print("Sorted array")
sort_type = sys.argv[1].lower()
start = time.time()
if sort_type == "insertionsort":
    InsertionSort(array)
else:
    print(Usage())
 
end = time.time()
if limit < 100:
    print("Sorted array")
    print(array)
print(f"{end-start} seconds")



