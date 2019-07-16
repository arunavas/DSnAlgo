-- Questions from "https://gist.github.com/pchiusano/bf06bd751395e1a6d09794b38f093787"

-- 1. Sum up a [Int], using explicit recursion.
sumOfInts :: [Int] -> Int
sumOfInts [] = 0
sumOfInts (x:xs) = x + sum xs

-- 2. Write a function to reverse a list, including its signature. If possible, use one of the fold combinators, rather than explicit recursion.
reverseList :: [a] -> [a]
reverseList = foldl (flip (:)) []

-- 3. Implement filter for lists.
filterList :: (a -> Bool) -> [a] -> [a]
filterList p = foldr applyP []
           where applyP e acc
                        | p e = e : acc
                        | otherwise = acc

-- 4. Implement filter and takeWhile using foldr.
-- filter implemented above
takeWhileList :: (a -> Bool) -> [a] -> [a]
takeWhileList p = foldr applyP []
              where applyP e acc
                           | p e = e : acc
                           | otherwise = []

test :: Applicative f => (f a, b) -> f (a, b)
test (fa, b) = (,) <$> fa <*> pure b

testList :: Applicative f => [(f a, b)] -> f [(a, b)]
testList = seqA . foldr (\e acc -> test e : acc)

seqA :: Applicative f => [f a] -> f [a]
seqA [] = pure []
seqA (x:xs) = (:) <$> x <$> (seqA xs)
