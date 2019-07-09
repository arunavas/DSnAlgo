import System.Environment

{-
Approach
Start iterating from begining to end with openBraceCount (oc) = 0
- if ith char is { then incr oc by 1
- if ith char is } and oc = 0 then record current index (flip current char) and incr oc by 1
- otherwise decr oc by 1
at this point
- if oc is 0 then expr is already balanced
- if oc is a odd number then balancing not possible
- otherwise, flip (oc / 2) open braces from end towards begining.

Time complexity - O (n+k) where n is number of elements in expr and k is half the difference between open brace and closed brace (after first n)
-}

type Expr = [Char]
type OpenCount = Int
type Count = Int
type Result = (Int, [Char])

--assumed Expr would only contain characters '{' and '}'
isBalanced :: Expr -> Bool
isBalanced = aux 0
  where aux oc [] = oc == 0
        aux oc (x:xs)
            | x == '{' = aux (oc + 1) xs
            | oc == 0 = False
            | otherwise = aux (oc - 1) xs

flipOpenBrace :: Count -> Expr -> Expr
flipOpenBrace _ [] = []
flipOpenBrace c (x:xs)
     | c == 0 = x:xs
     | x == '{' = '}' : flipOpenBrace (c - 1) xs
     | otherwise = x : flipOpenBrace c xs 

solve :: Expr -> OpenCount -> Result -> Maybe Result
solve [] oc (fc, res)
     | oc `mod` 2 /= 0 = Nothing
     | otherwise = let m = oc `div` 2 in Just (fc + m, reverse $ flipOpenBrace m res)
solve (x:xs) oc (fc, res)
     | x == '{'  = solve xs (oc + 1) (fc, x : res)
     | oc > 0    = solve xs (oc - 1) (fc, x : res)
     | otherwise = solve xs (oc + 1) (fc + 1, '{' : res)

solution :: Expr -> Maybe Result
solution expr = solve expr 0 (0, [])

main = getArgs >>= process
    where process [expr] = printRes . solution $ expr
          process _      = putStrLn "Usage: ./solution <expression>"
          printRes (Just (n, res)) = putStrLn ("Possible in " ++ (show n) ++ " inversions. Result: " ++ res)
          printRes Nothing         = putStrLn "Not Possible"
