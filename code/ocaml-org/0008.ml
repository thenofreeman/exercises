(* Eliminate Duplicates *)
(* Level: Intermediate *)

(* Eliminate consecutive duplicates of list elements. *)

(* Example: *)
(* # compress ["a"; "a"; "a"; "a"; "b"; "c"; "c"; "a"; "a"; "d"; "e"; "e"; "e"; "e"];; *)
(* - : string list = ["a"; "b"; "c"; "a"; "d"; "e"] *)

let compress = function
  | [] -> []
  | hd :: tl ->
    let rec compress prev acc = function
      | [] -> acc
      | hd :: tl ->
        if hd = prev then compress prev acc tl
        else compress hd (hd :: acc) tl

    in List.rev (compress hd [hd] tl)
