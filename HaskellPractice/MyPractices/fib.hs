fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 2) + fib (n - 1)

fSeries :: Int -> [Int]
fSeries n = case n of
            0 -> [0]
            _ -> fSeries (n - 1) ++ [fib n]
