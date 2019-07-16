factors :: Int -> [Int]
factors n = [x | x <- [1..n], n `mod` x == 0]

prime :: Int -> Bool
prime n | n < 1 = False
        | n == 1 = True
        | otherwise = factors n == [1,n]

primes :: Int -> [Int]
primes n = [x | x <- [1..n], prime x]
