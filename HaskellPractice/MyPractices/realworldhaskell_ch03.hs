len :: [a] -> Int
len = foldr (\_ l -> 1 + l) 0

mean :: (Num a, Fractional a) => [Int] -> a
mean l = fromIntegral (foldr (+) 0 l) / fromIntegral (len l)

revers :: [a] -> [a]
revers = foldl (flip (:)) []

toPalindrome :: [a] -> [a]
toPalindrome l = l ++ revers l

isPalindrome :: Eq a => [a] -> Bool
isPalindrome l = l == revers l

sortby :: Ord a => (a -> a -> Ordering) -> [a] -> [a]
sortby _ [] = []
sortby _ [x] = [x]
sortby f l @ (x:xs) = sortby f (filter (\e -> f e x == LT) xs) ++ filter (\e -> f e x == EQ) l ++ sortby f (filter (\e -> f e x == GT) xs)

qs :: Ord a => [a] -> [a]
qs [] = []
qs [x] = [x]
qs (x:xs) = (qs (filter (<x) xs)) ++ (x : filter (==x) xs) ++ (qs (filter (>x) xs))

sortByLenOfList :: Ord a => [[a]] -> [[a]]
sortByLenOfList = sortby (\a b -> len a `compare` len b)

