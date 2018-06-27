import Control.Monad ((<=<), guard)
import Data.List (partition, nub, intercalate)
import qualified Data.Map as M

{-
a. filter ponds - O(n)
b. find shortest path (using dijkstra's algorithm**) from pond1 to pond2 until there is only one pond left in the matrix - O (n)
**a little tweek - instead of 1 node to another node, we need to consider group of node to another group of node*.
*getting next of group of nodes = union (for each nodes in the group get next nodes)

Time Complexity - O(n+pn) where n is number of cells in matrix and p is number of p (found from first O(n)).
Space Complexity - O(p) where p is number of ponds it has initially (though haskell is lazy by pond will consume extra space as it is generated eagerly).
-}

type Board    = [[Char]]
type Position = (Int, Int)
type Field    = (Position, Char)
type Pond     = [Position]
type Water    = Field

nextPositions :: Position -> [Position]
nextPositions (x, y) = [(x-1,y-1),(x-1,y),(x-1,y+1),(x,y+1),(x+1,y+1),(x+1,y),(x+1,y-1),(x,y-1)]

linkedPositions :: (Position -> Bool) -> Position -> [Position]
linkedPositions f p = filter f . nextPositions $ p

isSingleton :: [Field] -> Bool
isSingleton = and . (\ps -> ps >>= return . not . null . linkedPositions (`elem` ps)) . toWaterPositions

toWaterPositions :: [Field] -> [Position]
toWaterPositions = map fst . filter (\(_, c) -> c == 'W')

toFields :: Board -> [Field]
toFields b = guard (isValidBoard b) >> ((\(x, l) -> map (\(y, c) -> ((x, y), c)) l) <=< zip [0..] . map (zip [0..])) b

isValidBoard :: Board -> Bool
isValidBoard [] = True
isValidBoard (x:xs) = let l = length x in all (\y -> l == length y) xs

validPositions :: M.Map Position Char -> Position -> [Position]
validPositions m p = nextPositions p >>= \n -> guard (M.member n m) >> return n

isNeighbourPosition :: Position -> Position -> Bool
isNeighbourPosition (x, y) (x', y') = abs (x - x') <= 1 && abs(y - y') <= 1

ponds :: [Pond] -> [Position] -> [Pond]
ponds ps []     = reverse ps
ponds ps (x:xs) = let (a, b) = partition (isNeighbourPosition x) xs in ponds ((x:a):ps) b

filterNot :: (a -> Bool) -> [a] -> [a]
filterNot f = filter (not . f)

shortestPath :: M.Map Position Char -> [Position] -> [[Position]] -> [[Position]]
shortestPath m t [] = [t]
shortestPath m t ll = let ns = (ll >>= \l -> l >>= filterNot (`elem` l) . validPositions m >>= \p -> return (p:l))
                      in case partition (\(x:xs) -> x `elem` t) ns of
                         ([], ps) -> shortestPath m t ps
                         (rs, _)  -> nub $ rs >>= \r -> return $ foldr (:) r (filterNot (`elem` r) t)

solution :: Board -> [[Position]]
solution b = let fs = toFields b
                 fm = M.fromList fs
                 ws = toWaterPositions fs
             in solve fm (ponds [] ws) []
    where solve fm [] acc     = map (filterLands fm) acc
          solve fm (x:xs) acc = let nps = shortestPath fm x acc in solve fm xs nps
          filterLands fm rs = filter (\p -> 'L' == (M.findWithDefault ' ' p fm)) rs

ps = map fst . toFields $ board
board = [['W', 'W', 'L', 'L', 'L'], ['L', 'L', 'L', 'W', 'L'], ['L', 'W', 'L', 'L', 'L'], ['L', 'L', 'W', 'L', 'W']]
brd = ["WWLLL","LLLWL","LWLLL","LLWLW"]
