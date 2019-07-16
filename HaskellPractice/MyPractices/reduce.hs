reduceL :: [a] -> (a -> a -> a) -> Maybe a
reduceL [] _     = Nothing
reduceL (x:xs) f = Just $ foldl f x xs

reduceR :: [a] -> (a -> a -> a) -> Maybe a
reduceR [] _     = Nothing
reduceR (x:xs) f = Just $ foldr f x xs
