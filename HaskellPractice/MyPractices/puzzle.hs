
permMul :: [Int] -> [(Int, Int, Int, [Int])]
permMul l = uniq3 `filter` (perm2 l >>= (\(n, r) -> (\x -> (n, x, n*x, filterAll [x, (n*x)] r)) `map` r))


permAdd :: (Int, Int, Int, [Int]) -> [(Int, Int, Int, Int, Int, [Int])]
permAdd (a, b, c, l) = perm2 l >>= \(x, _) -> [(a, b, c, x, c+x, filterAll [x, c+x] l)]

perm2 :: [Int] -> [(Int, [Int])]
perm2 = aux []
  where aux :: [Int] -> [Int] -> [(Int, [Int])]
        aux _ [] = []
        aux l (n:ns) = (++ aux (n:l) ns) . map (\x -> (n * 10 + x, filterL x (l++ns))) $ l ++ ns

filterAll :: [Int] -> [Int] -> [Int]
filterAll f l = foldr (\a b -> filterL a b) l f

filterL :: Int -> [Int] -> [Int]
filterL 0 l = l
filterL n l = filterL (n `div` 10) $ (/= (n `mod` 10)) `filter` l

uniq3 :: (Int, Int, Int, [Int]) -> Bool
uniq3 (a, b, c, l) = c < 100 && c `mod` 10 /= 0 && uniq a (adds [b, c] l) && uniq b (adds [a, c] l) && uniq c (adds [a, b] l)

uniq5 :: (Int, Int, Int, Int, Int, [Int]) -> Bool
uniq5 (a, b, c, x, y, l) = null l && c < 100 && c `mod` 10 /= 0 && y < 100 && y `mod` 10 /= 0
                           && uniq a (adds [b, c, x, y] l) && uniq b (adds [a, c, x, y] l) && uniq c (adds [a, b, x, y] l)
                           && uniq x (adds [a, b, c, y] l) && uniq y (adds [a, b, c, x] l)

adds :: [Int] -> [Int] -> [Int]
adds ns ls = ns >>= (\e -> addElem e ls)

addElem :: Int -> [Int] -> [Int]
addElem 0 l = l
addElem n l = addElem (n `div` 10) $ (n `mod` 10) : l

uniq :: Int -> [Int] -> Bool
uniq 0 _ = True
uniq n l = (not $ (n `mod` 10) `elem` l) && uniq (n `div` 10) l

solution :: [Int] -> [(Int, Int, Int, Int, Int, [Int])]
solution l = uniq5 `filter` (permMul l >>= permAdd)

l :: [Int]
l = [1,2,3,4,5,6,7,8,9]