data Operator = / | * | + | - deriving (Show, Eq, Ord, Enum)

evalExpr :: [Char] -> Int
evalExpr [] = 0
evalExpr (x:xs) =

isNumber :: 
