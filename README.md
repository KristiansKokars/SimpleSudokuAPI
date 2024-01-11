# Simple Sudoku API

An API server with two routes for generating a random sudoku board and verifying if the sent sudoku board is a valid solveable one.

```
GET / 
  - Request Body: None
  - Response Body: 9x9 2D Int Array with valid numbers in all spots (for example, [[9, 5, 3, 4, ...], [...], ...])
POST /verify -> returns a Boolean of whether the given 9x9 2D Int array is solved correctly, 
  - Request Body: 9x9 2D Int Array (for example, [[9, 5, 3, 4, ...], [...], ...])
  - Response Body: Boolean, whether the sudoku board is solved correctly or not
```
