splitWith :: (a -> Bool) -> [a] -> [[a]]
splitWith _ [] = []
splitWith f l = foldr (\e acc @ (x:xs) -> if f e then [] : acc else (e : x) : xs) [[]] l

foldR :: (b -> a -> a) -> a -> [b] -> a
foldR _ acc [] = acc
foldR f acc (x:xs) = f x (foldR f acc xs)

foldL :: (a -> b -> a) -> a -> [b] -> a
foldL _ acc [] = acc
foldL f acc (x:xs) = foldL f (f acc x) xs

myFoldl :: (a -> b -> a) -> a -> [b] -> a
myFoldl f z xs = foldr step id xs z
    where step x g a = g (f a x)

x f z xs = foldr (\x g a -> g (f a x)) id xs z
