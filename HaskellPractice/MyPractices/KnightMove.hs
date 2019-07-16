import Control.Monad
import Data.List (find)

class Next a where
    next :: a -> Direction -> a

newtype Position = Position (Int, Int)
instance Show Position where
    show (Position (x, y)) = "(" ++ show x ++ "," ++ show y ++ ")"
instance Eq Position where
    Position (a, b) == Position (x, y) = a == x && b == y
instance Next Position where
    next (Position (x, y)) UL = Position (x - 1, y - 2)
    next (Position (x, y)) UR = Position (x + 1, y - 2)
    next (Position (x, y)) RU = Position (x + 2, y - 1)
    next (Position (x, y)) RD = Position (x + 2, y + 1)
    next (Position (x, y)) DR = Position (x + 1, y + 2)
    next (Position (x, y)) DL = Position (x - 1, y + 2)
    next (Position (x, y)) LD = Position (x - 2, y + 1)
    next (Position (x, y)) LU = Position (x - 2, y - 1)

data Direction = UL | UR | RU | RD | DR | DL | LD | LU deriving (Show, Eq, Ord, Enum, Bounded)
minDirection :: Direction
minDirection = minBound :: Direction
maxDirection :: Direction
maxDirection = maxBound :: Direction

newtype Move = Move (Position, Maybe Direction)
instance Show Move where
    show (Move (p, Nothing)) = "<" ++ show p ++ ">"
    show (Move (p, Just d)) = "<" ++ show p ++ "-" ++ show d ++ ">"

movesToPositions :: [Move] -> [Position]
movesToPositions = map (\(Move (p, _)) -> p)

filterNot :: (a -> Bool) -> [a] -> [a]
filterNot p = filter (not . p)

guardPosition :: Position -> [Position]
guardPosition p @ (Position (x, y)) = guard(x >= 1 && x <= 8 && y >= 1 && y <= 8) >> return p

nextStep :: Position -> Direction -> [Position]
nextStep p = guardPosition . next p

nextSteps :: Position -> [Position]
nextSteps p = [minDirection .. maxDirection] >>= nextStep p

nextMoves :: Position -> [Move]
nextMoves p = [minDirection .. maxDirection] >>= \d -> nextStep p d >>= \s -> return (Move (s, Just d))

nextUnvisitedSteps :: [Position] -> Position -> [Position]
nextUnvisitedSteps l = filterNot (`elem` l) . nextSteps

nextUnvisitedMoves :: [Position] -> Position -> [Move]
nextUnvisitedMoves l = filterNot (\(Move(p, _)) -> p `elem` l) . nextMoves

proceedNext :: [[Move]] -> [[Move]]
proceedNext [] = []
proceedNext ll = ll >>= \l @ (Move(p, _):xs) -> (nextUnvisitedMoves (movesToPositions xs) p) >>= \e -> return (e:l)

calculateShortestPath :: Position -> [[Move]] -> [Move]
calculateShortestPath _ [] = []
calculateShortestPath trgt ll = case find (\x -> (take 1 $ movesToPositions x) == [trgt]) ll of
    Nothing -> calculateShortestPath trgt . proceedNext $ ll 
    Just res -> res

shortestPath :: Position -> Position -> [Move]
shortestPath src trgt = guardPosition src >>= \p -> calculateShortestPath trgt (return [Move (p, Nothing)]) 

minPath :: Position -> Position -> Int
minPath src trgt = (length $ shortestPath src trgt) - 1
