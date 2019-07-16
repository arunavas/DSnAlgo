import Control.Monad (guard, foldM, forM, forM_, (<=<))
import System.Directory (doesDirectoryExist, getDirectoryContents, getPermission)
import System.FilePath ((</>))

import System.Environment (getArgs)

type FileName = [Char]
type Pattern = [Char]

matches :: Pattern -> FileName -> Bool
matches [] []         = True
matches (x:xs) (y:ys) 
    | x == '*'  = null xs || xs `matches` (dropWhile (/= head xs) (y:ys))
    | otherwise = (x == '?' || x == y) && xs `matches` ys
matches _ _           = False

matchesPath :: Pattern -> FilePath -> Bool
matchesPath p = any (matches p) . splitWith (=='/')

filterPattern :: Pattern -> [FileName] -> [FileName]
filterPattern p = filter (matches p)
{-
listContents :: FilePath -> IO [FilePath]
listContents path = getDirectoryContents path >>= process . map (path </>) . filterNot (`elem` [".", ".."])
    where process :: [FilePath] -> IO [FilePath]
          process ps = (forM ps $ \p -> doesDirectoryExist p >>= \r -> if r then listContents p else return [p]) >>= return . concat

findSimple :: Pattern -> FilePath -> IO [FilePath]
findSimple ptrn path = listContents path >>= \ps -> return $ ps >>= \p -> guard (any (matches ptrn) $ splitWith (=='/') p) >> return p
-}
simpleFind :: Pattern -> FilePath -> Bool -> IO ()
simpleFind ptrn path isDir
  | matchesPath ptrn path = putStrLn path
  | isDir                 = getDirectoryContents path >>= process . map (path </>) . filterNot (`elem` [".", ".."])
  | otherwise             = return ()
    where process ps = forM_ ps $ \p -> simpleFind ptrn p <=< doesDirectoryExist $ p

simpleFind' :: Pattern -> FilePath -> Bool -> IO [FilePath]
simpleFind' ptrn path isDir
  | matchesPath ptrn path = return [path]
  | isDir                 = getDirectoryContents path >>= process . map (path </>) . filterNot (`elem` [".", ".."])
  | otherwise             = return []
    where process ps = (forM ps $ \p -> simpleFind' ptrn p <=< doesDirectoryExist $ p) >>= return . concat

main = getArgs >>= process
    where process ("-S":ptrn:path:[]) = doesDirectoryExist path >>= simpleFind' ptrn path >>= mapM_ putStrLn
          process ("-s":ptrn:path:[]) = doesDirectoryExist path >>= simpleFind ptrn path 
          process (ptrn:path:[])      = putStrLn "Not yet implemented!"
          process _                   = putStrLn "Usage: ./find [-s] <pattern> <path>" 

--Utility Functions--
splitWith :: (a -> Bool) -> [a] -> [[a]]
splitWith _ [] = []
splitWith f l = foldr (\e acc @ (x:xs) -> if f e then [] : acc else (e : x) : xs) [[]] l

filterNot :: (a -> Bool) -> [a] -> [a]
filterNot f = filter (not.f)

splitM :: (a -> IO Bool) -> [a] -> IO ([a], [a])
splitM f l = foldM (\(a, b) e -> f e >>= \r -> return $ if r then (e:a, b) else (a, e:b)) ([], []) l
