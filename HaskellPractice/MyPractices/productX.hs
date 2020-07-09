/*
Need to make product X with q quantities of n ingredients.
Given number of required quantities and available quantities of n ingredients,
  find out the number of product X possible to make with q quantities of all n ingredients.
*/
main = do
    n <- getLine
    reqQtyLine <- getLine
    avlQtyLine <- getLine
    let reqQty = map convertToInteger . words $ reqQtyLine
        avlQty = map convertToInteger . words $ avlQtyLine
    putStrLn $ show $ solution reqQty avlQty

solution :: [Integer] -> [Integer] -> Integer
solution req avl
    | length req /= length avl = 0
    | otherwise = let res = minimum . map (\(a, b) -> a `div` b) . zip avl $ req
                  in if res < 0 then 0 else res

convertToInteger :: String -> Integer
convertToInteger val = read val :: Integer
