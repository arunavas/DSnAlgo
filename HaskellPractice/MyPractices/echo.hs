import System.IO

sGetLine :: IO String
sGetLine = do x <- getSecretChar
              if x == '\n' then
                 do putChar x
                    return []
              else
                 do putChar '*'
                    xs <- sGetLine
                    return (x:xs)


getSecretChar :: IO Char
getSecretChar = do hSetEcho stdin False
                   c <- getChar
                   hSetEcho stdin True
                   return c
