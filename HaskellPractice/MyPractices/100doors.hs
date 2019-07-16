toggle n
  | n == 0    = 1
  | otherwise = 0

recur :: Int -> [Int] -> [Int]
recur n l
  | n > 100   = l
  | otherwise = recur (n + 1) . snd . foldr (\i (c, a) -> (c - 1, if c `mod` n == 0 then (toggle i) : a else i : a)) (100, []) $ l
