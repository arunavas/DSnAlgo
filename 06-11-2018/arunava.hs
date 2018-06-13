import Data.List (partition, find)
import Control.Monad

type Board = [[Int]]
type Index = (Int, Int)
type Size = (Int, Int)
type Cost = Int
type Path = [Position]

data Direction = L | R | U | D deriving (Show, Enum, Bounded)
data Position = Position { getCost :: Cost, getPos :: Index }
instance Show Position where
    show (Position c (x, y)) = "<" ++ show c ++ "-(" ++ show x ++ "," ++ show y ++ ")>"
instance Eq Position where
    (Position c1 (x1, y1)) == (Position c2 (x2, y2)) = x1 == x2 && y1 == y2

guardPos :: Cost -> [Position] -> Path -> Position -> Path
guardPos _ _ [] _ = []
guardPos cost ps pp@((Position c pi):_) cp@(Position _ (x', y')) = case find (==cp) pp of
         Nothing -> case find (==cp) ps of
                            Nothing -> []
                            Just (Position c' _) -> if (c + c') <= cost then [Position (c + c') (x', y')] else []
         Just _ -> []

move :: Position -> Direction -> Position
move (Position c (x, y)) L = Position c (x, y - 1)
move (Position c (x, y)) R = Position c (x, y + 1)
move (Position c (x, y)) U = Position c (x - 1, y)
move (Position c (x, y)) D = Position c (x + 1, y)

nextMoves :: Cost -> [Position] -> Path -> Path
nextMoves _ _ [] = []
nextMoves cost ps pps@(curPos:_) = map (move curPos) [L .. D] >>= guardPos cost ps pps

shortestPath :: Cost -> [Position] -> [Path] -> [Path]
shortestPath _ _ [] = []
shortestPath c ps acc = case partition (\(p:_) -> getCost p == c) acc of
             ([], rest) -> shortestPath c ps $ rest >>= \l -> nextMoves c ps l >>= \x -> return (x:l)
             (res, _) -> map reverse res

convert :: Board -> [Position]
convert = (\(x, l) -> map (\(y, c) -> Position c (x, y)) l) <=< zip [0..] . map (zip [0..])

solution :: Int -> Board -> [Path]
solution _ [] = []
solution cost b = case convert b of
             [] -> []
             all @ (x:_) -> shortestPath cost all [[x]]
