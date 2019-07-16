import Data.IORef

main :: IO ()
main = newIORef ([] :: [Int]) >>= operate

operate :: IORef [Int] -> IO ()
operate box = readIORef box >>= \l -> getLine >>= \ch -> case ch of
                "Q"   -> putStrLn "QUITed"
                "SUM" -> putStrLn $ "SUM: " ++ (show $ sumList l)
                _     -> do
                           if isInt ch then writeIORef box $ (read ch :: Int) : l else putStrLn "Invalid entry!" >> showHelp
                           operate box

showHelp :: IO ()
showHelp = do
             putStrLn ".------------------------------------------------------."
             putStrLn "|-H               -> This Message                      |"
             putStrLn "|-Q               -> Quit                              |"
             putStrLn "|-SUM             -> Sum of all numbers added till now |"
             putStrLn "|-<any integer> n -> add n to the list                 |"
             putStrLn "'------------------------------------------------------'"

sumList :: [Int] -> Int
sumList = foldr (+) 0

isInt :: String -> Bool
isInt = all (`elem` ['0'..'9'])
