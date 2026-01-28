(* Run-Length Encoding *)
(* Level: Beginner *)

(* If you need so, refresh your memory about run-length encoding. *)

(* Example: *)
(* # encode ["a"; "a"; "a"; "a"; "b"; "c"; "c"; "a"; "a"; "d"; "e"; "e"; "e"; "e"];; *)
(* - : (int * string) list = *)
(* [(4, "a"); (1, "b"); (2, "c"); (2, "a"); (1, "d"); (4, "e")] *)

let encode = function
  | [] -> []
  | hd :: tl -> begin
    let rec encode c n acc = function
        | [] -> (n, c) :: acc
        | hd :: tl -> begin
            if hd = c then encode c (n + 1) acc tl
            else encode hd 1 ((n, c) :: acc) tl
        end
    in List.rev (encode hd 1 [] tl)
  end;;
