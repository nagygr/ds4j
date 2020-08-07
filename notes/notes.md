---
title: NOTES FOR DS4J
subtitle: design ideas on a scratchpad
author:
- Gergely Nagy
geometry:
- top=10mm
- left=20mm
- right=20mm
papersize: a4
linkcolor: blue
...

# Machine
The machine is a virtual, domain-specific CPU that is tailored for parsing. Its
commands are the atoms of processing a text that conforms to a formal grammar.

## Rules of the grammar
The individual rules need not have their own starting command, they are
addresses in the code memory. The address of the starting rule should be noted
in a variable as that is the point where the parsing starts. The other starting
addresses can be hardcoded into the grammar, although as they are needed
throughout the building of the code memory, they can just as well be saved for
later use.

There is one thing a rule command must do: they must add a new node to the AST
and then they need to fill it with information (the starting and closing index
of the matched substring).

They must also return:

- a boolean value saying whether they succeeded or not
- if they succeeded: the starting and closing index of the matched substring
- if they failed: they must take a look at the failure variable and add their
  name to it if it's missing

## Terminal rules
A simple terminal rule (like `Character`) needs as input:

- the Context that contains the current position in the processed file (index)
- the length of the file to compare the index against
- the variable that stores the information for a failure position

As an output they must provide:

- a boolean value saying whether they succeeded or not
- if they succeeded: the starting and closing index of the matched substring
- if they failed: a possible entry to the failure position variable (only of
  their read position is further into the file than the previous failure
  position).

## One-operand operators
These are `Option`, `Repetition` (`Kleene`), `RepetitionOrEpsilon`
(`OneOrMore`/`Plus`).

They are a bit like a function call or loop. They need a return command that
goes back to the start where the results can be processed and another iteration
can be started if needed.

They need to know whether it's the first time they are hit or the second or
more: as at the first time they only need to jump to the rule that they surround
(inner rule), while in the second and later turns they need to process the
results and decide whether they should do another iteration or return true or
false.

Maybe the command that inner rule returns to should be a different one.

## Two-operand operators
These are `Alternation` and `Concatenation`.

The situation is similar to the one-operand operator only more complex as here
there are two inner rules and thus three different states (start, first
succeeded/failed, second succeeded/failed).
