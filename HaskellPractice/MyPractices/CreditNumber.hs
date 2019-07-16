validateCreditNumber :: [Char] -> Bool
validateCreditNumber = (==0) . (`mod` 10) . fst . foldr (\n (acc, idx) -> (acc + f idx n, idx + 1)) (0, 1) . map toNumber
                            where f :: Int -> Int -> Int
                                  f idx n | idx `mod` 2 == 0 = guard (n + n)
                                          | otherwise        = n
                                    where guard :: Int -> Int
                                          guard n | n > 9     = (n `div` 10) + (n `mod` 10)
                                                  | otherwise = n


toNumber :: Char -> Int
toNumber '0' = 0
toNumber '1' = 1
toNumber '2' = 2
toNumber '3' = 3
toNumber '4' = 4
toNumber '5' = 5
toNumber '6' = 6
toNumber '7' = 7
toNumber '8' = 8
toNumber '9' = 9
toNumber _   = -145 --Max sum of 16 single chars can be 144
