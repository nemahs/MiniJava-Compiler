.section ".text"
.global main
.align 4

!------main----
main:
	save %sp,-96,%sp
	SET 24,%o0
	CALL _alloc_object
	NOP
	MOV %o0,%l0
	MOV %l0,%o0
	SET 10,%o1
	CALL BBS$Start002
	NOP
	MOV %o0,%l1
	MOV %l1,%o0
	CALL println
	NOP
	MOV %o0,%l2
	BA mainFuncEnd
	NOP
mainFuncEnd:
	exit_program

!------Start007----
BBS$Start002:
	save %sp,-252&-8,%sp
	SUB %fp,4,%l0
	MOV %i0,%o0
	MOV %i1,%o1
	CALL BBS$Init005
	NOP
	MOV %o0,%l1
	ST %l1,[%l0]
	SUB %fp,4,%l2
	MOV %i0,%o0
	CALL BBS$Print004
	NOP
	MOV %o0,%l3
	ST %l3,[%l2]
	SET 99999,%o0
	CALL println
	NOP
	MOV %o0,%l4
	SUB %fp,4,%l5
	MOV %i0,%o0
	CALL BBS$Sort003
	NOP
	MOV %o0,%l6
	ST %l6,[%l5]
	SUB %fp,4,%l7
	MOV %i0,%o0
	CALL BBS$Print004
	NOP
		LD [%fp - 8],%g3
	MOV %o0,%g3
		ST %g3,[%fp - 8]
		LD [%fp - 8],%g1
	ST %g1,[%l7]
	SET 0,%i0
	BA Start007FuncEnd
	NOP
Start007FuncEnd:
	ret
	restore

!------Sort008----
BBS$Sort003:
	save %sp,-520&-8,%sp
	SUB %i0,8,%l0
	LD [%l0],%l1
	SUB %l1,1,%l2
	ST %l2,[%fp-8]
	SET -1,%l3
	ST %l3,[%fp-12]
	BA again011
	NOP
again011:
	SUB %fp,12,%l4
	LD [%l4],%l5
	SUB %fp,8,%l6
	LD [%l6],%l7
	CMP %l5,%l7
	BL continue010
	NOP
	BA end009
	NOP
end009:
	SET 0,%i0
	BA Sort008FuncEnd
	NOP
continue010:
		LD [%fp - 40],%g3
	SET 1,%g3
		ST %g3,[%fp - 40]
		LD [%fp - 40],%g1
	ST %g1,[%fp-32]
	BA again014
	NOP
again014:
		LD [%fp - 44],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 44]
		LD [%fp - 44],%g1
		LD [%fp - 48],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 48]
		LD [%fp - 52],%g3
	SUB %fp,8,%g3
		ST %g3,[%fp - 52]
		LD [%fp - 52],%g1
		LD [%fp - 56],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 56]
		LD [%fp - 56],%g1
		LD [%fp - 60],%g3
	ADD 1,%g1,%g3
		ST %g3,[%fp - 60]
		LD [%fp - 48],%g1
		LD [%fp - 60],%g2
	CMP %g1,%g2
	BL continue013
	NOP
	BA end012
	NOP
end012:
		LD [%fp - 64],%g3
	SUB %fp,8,%g3
		ST %g3,[%fp - 64]
		LD [%fp - 64],%g1
		LD [%fp - 68],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 68]
		LD [%fp - 68],%g1
		LD [%fp - 72],%g3
	SUB %g1,1,%g3
		ST %g3,[%fp - 72]
		LD [%fp - 72],%g1
	ST %g1,[%fp-8]
	BA again011
	NOP
continue013:
		LD [%fp - 76],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 76]
		LD [%fp - 76],%g1
		LD [%fp - 80],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 80]
		LD [%fp - 80],%g1
		LD [%fp - 84],%g3
	SUB %g1,1,%g3
		ST %g3,[%fp - 84]
		LD [%fp - 84],%g1
	ST %g1,[%fp-28]
		LD [%fp - 88],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 88]
		LD [%fp - 88],%g1
		LD [%fp - 92],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 92]
		LD [%fp - 96],%g3
	SUB %fp,28,%g3
		ST %g3,[%fp - 96]
		LD [%fp - 96],%g1
		LD [%fp - 100],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 100]
		LD [%fp - 100],%g1
		LD [%fp - 104],%g3
	MOV %g1,%g3
		ST %g3,[%fp - 104]
		LD [%fp - 104],%g1
		LD [%fp - 104],%g3
	ADD %g1,1,%g3
		ST %g3,[%fp - 104]
		LD [%fp - 104],%g1
		LD [%fp - 104],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 104]
		LD [%fp - 92],%g1
		LD [%fp - 104],%g2
		LD [%fp - 92],%g3
	ADD %g1,%g2,%g3
		ST %g3,[%fp - 92]
		LD [%fp - 92],%g1
		LD [%fp - 108],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 108]
		LD [%fp - 108],%g1
	ST %g1,[%fp-16]
		LD [%fp - 112],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 112]
		LD [%fp - 112],%g1
		LD [%fp - 116],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 116]
		LD [%fp - 120],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 120]
		LD [%fp - 120],%g1
		LD [%fp - 124],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 124]
		LD [%fp - 124],%g1
		LD [%fp - 128],%g3
	MOV %g1,%g3
		ST %g3,[%fp - 128]
		LD [%fp - 128],%g1
		LD [%fp - 128],%g3
	ADD %g1,1,%g3
		ST %g3,[%fp - 128]
		LD [%fp - 128],%g1
		LD [%fp - 128],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 128]
		LD [%fp - 116],%g1
		LD [%fp - 128],%g2
		LD [%fp - 116],%g3
	ADD %g1,%g2,%g3
		ST %g3,[%fp - 116]
		LD [%fp - 116],%g1
		LD [%fp - 132],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 132]
		LD [%fp - 132],%g1
	ST %g1,[%fp-20]
		LD [%fp - 136],%g3
	SET 1,%g3
		ST %g3,[%fp - 136]
		LD [%fp - 140],%g3
	SUB %fp,20,%g3
		ST %g3,[%fp - 140]
		LD [%fp - 140],%g1
		LD [%fp - 144],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 144]
		LD [%fp - 148],%g3
	SUB %fp,16,%g3
		ST %g3,[%fp - 148]
		LD [%fp - 148],%g1
		LD [%fp - 152],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 152]
		LD [%fp - 144],%g1
		LD [%fp - 152],%g2
	CMP %g1,%g2
	BL if$then018
	NOP
	BA if$else019
	NOP
if$else019:
		LD [%fp - 136],%g3
	SET 0,%g3
		ST %g3,[%fp - 136]
	BA if$then018
	NOP
if$then018:
		LD [%fp - 136],%g1
	CMP %g1,1
	BE if$then015
	NOP
	BA if$else016
	NOP
if$else016:
		LD [%fp - 156],%g3
	MOV %g0,%g3
		ST %g3,[%fp - 156]
		LD [%fp - 156],%g1
	ST %g1,[%fp-4]
	BA if$end017
	NOP
if$end017:
		LD [%fp - 160],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 160]
		LD [%fp - 160],%g1
		LD [%fp - 164],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 164]
		LD [%fp - 164],%g1
		LD [%fp - 168],%g3
	ADD 1,%g1,%g3
		ST %g3,[%fp - 168]
		LD [%fp - 168],%g1
	ST %g1,[%fp-32]
	BA again014
	NOP
if$then015:
		LD [%fp - 172],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 172]
		LD [%fp - 172],%g1
		LD [%fp - 176],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 176]
		LD [%fp - 176],%g1
		LD [%fp - 180],%g3
	SUB %g1,1,%g3
		ST %g3,[%fp - 180]
		LD [%fp - 180],%g1
	ST %g1,[%fp-24]
		LD [%fp - 184],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 184]
		LD [%fp - 184],%g1
		LD [%fp - 188],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 188]
		LD [%fp - 192],%g3
	SUB %fp,24,%g3
		ST %g3,[%fp - 192]
		LD [%fp - 192],%g1
		LD [%fp - 196],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 196]
		LD [%fp - 196],%g1
		LD [%fp - 200],%g3
	MOV %g1,%g3
		ST %g3,[%fp - 200]
		LD [%fp - 200],%g1
		LD [%fp - 200],%g3
	ADD %g1,1,%g3
		ST %g3,[%fp - 200]
		LD [%fp - 200],%g1
		LD [%fp - 200],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 200]
		LD [%fp - 188],%g1
		LD [%fp - 200],%g2
		LD [%fp - 188],%g3
	ADD %g1,%g2,%g3
		ST %g3,[%fp - 188]
		LD [%fp - 188],%g1
		LD [%fp - 204],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 204]
		LD [%fp - 204],%g1
	ST %g1,[%fp-36]
		LD [%fp - 208],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 208]
		LD [%fp - 208],%g1
		LD [%fp - 212],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 212]
		LD [%fp - 216],%g3
	SUB %fp,24,%g3
		ST %g3,[%fp - 216]
		LD [%fp - 216],%g1
		LD [%fp - 220],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 220]
		LD [%fp - 220],%g1
		LD [%fp - 224],%g3
	MOV %g1, %g3
		ST %g3,[%fp - 224]
		LD [%fp - 224],%g1
		LD [%fp - 224],%g3
	ADD 1,%g1,%g3
		ST %g3,[%fp - 224]
		LD [%fp - 224],%g1
		LD [%fp - 224],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 224]
		LD [%fp - 212],%g1
		LD [%fp - 224],%g2
		LD [%fp - 212],%g3
	ADD %g1,%g2,%g3
		ST %g3,[%fp - 212]
		LD [%fp - 228],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 228]
		LD [%fp - 228],%g1
		LD [%fp - 232],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 232]
		LD [%fp - 236],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 236]
		LD [%fp - 236],%g1
		LD [%fp - 240],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 240]
		LD [%fp - 240],%g1
		LD [%fp - 244],%g3
	MOV %g1,%g3
		ST %g3,[%fp - 244]
		LD [%fp - 244],%g1
		LD [%fp - 244],%g3
	ADD %g1,1,%g3
		ST %g3,[%fp - 244]
		LD [%fp - 244],%g1
		LD [%fp - 244],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 244]
		LD [%fp - 232],%g1
		LD [%fp - 244],%g2
		LD [%fp - 232],%g3
	ADD %g1,%g2,%g3
		ST %g3,[%fp - 232]
		LD [%fp - 232],%g1
		LD [%fp - 248],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 248]
		LD [%fp - 248],%g1
		LD [%fp - 212],%g3
	ST %g1, [%g3]
		ST %g3,[%fp - 212]
		LD [%fp - 252],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 252]
		LD [%fp - 252],%g1
		LD [%fp - 256],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 256]
		LD [%fp - 260],%g3
	SUB %fp,32,%g3
		ST %g3,[%fp - 260]
		LD [%fp - 260],%g1
		LD [%fp - 264],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 264]
		LD [%fp - 264],%g1
		LD [%fp - 268],%g3
	MOV %g1, %g3
		ST %g3,[%fp - 268]
		LD [%fp - 268],%g1
		LD [%fp - 268],%g3
	ADD 1,%g1,%g3
		ST %g3,[%fp - 268]
		LD [%fp - 268],%g1
		LD [%fp - 268],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 268]
		LD [%fp - 256],%g1
		LD [%fp - 268],%g2
		LD [%fp - 256],%g3
	ADD %g1,%g2,%g3
		ST %g3,[%fp - 256]
		LD [%fp - 272],%g3
	SUB %fp,36,%g3
		ST %g3,[%fp - 272]
		LD [%fp - 272],%g1
		LD [%fp - 276],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 276]
		LD [%fp - 276],%g1
		LD [%fp - 256],%g3
	ST %g1, [%g3]
		ST %g3,[%fp - 256]
	BA if$end017
	NOP
Sort008FuncEnd:
	ret
	restore

!------Print020----
BBS$Print004:
	save %sp,-276&-8,%sp
	MOV %g0,%l0
	ST %l0,[%fp-4]
	BA again023
	NOP
again023:
	SUB %fp,4,%l1
	LD [%l1],%l2
	SUB %i0,8,%l3
	LD [%l3],%l4
	CMP %l2,%l4
	BL continue022
	NOP
	BA end021
	NOP
end021:
	SET 0,%i0
	BA Print020FuncEnd
	NOP
continue022:
	SUB %i0,4,%l5
	LD [%l5],%l6
	SUB %fp,4,%l7
		LD [%fp - 8],%g3
	LD [%l7],%g3
		ST %g3,[%fp - 8]
		LD [%fp - 8],%g1
		LD [%fp - 12],%g3
	MOV %g1,%g3
		ST %g3,[%fp - 12]
		LD [%fp - 12],%g1
		LD [%fp - 12],%g3
	ADD %g1,1,%g3
		ST %g3,[%fp - 12]
		LD [%fp - 12],%g1
		LD [%fp - 12],%g3
	SMUL %g1,4,%g3
		ST %g3,[%fp - 12]
		LD [%fp - 12],%g2
	ADD %l6,%g2,%l6
		LD [%fp - 16],%g3
	LD [%l6],%g3
		ST %g3,[%fp - 16]
		LD [%fp - 16],%g1
	MOV %g1,%o0
	CALL println
	NOP
		LD [%fp - 20],%g3
	MOV %o0,%g3
		ST %g3,[%fp - 20]
		LD [%fp - 24],%g3
	SUB %fp,4,%g3
		ST %g3,[%fp - 24]
		LD [%fp - 24],%g1
		LD [%fp - 28],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 28]
		LD [%fp - 28],%g1
		LD [%fp - 32],%g3
	ADD 1,%g1,%g3
		ST %g3,[%fp - 32]
		LD [%fp - 32],%g1
	ST %g1,[%fp-4]
	BA again023
	NOP
Print020FuncEnd:
	ret
	restore

!------Init024----
BBS$Init005:
	save %sp,-388&-8,%sp
	ST %i1,[%i0-8]
	SUB %i0,4,%l0
	SMUL %i1,4,%l1
	MOV %l1,%o0
	CALL _alloc_object
	NOP
	MOV %o0,%l2
	ADD 1,%i1,%l3
	ST %l3,[%l2]
	ST %l2,[%l0]
	SUB %i0,4,%l4
	LD [%l4],%l5
SET 4,%l6
ADD %l5,%l6,%l5
	SET 20,%l7
	ST %l7,[%l5]
		LD [%fp - 4],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 4]
		LD [%fp - 4],%g1
		LD [%fp - 8],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 8]
		LD [%fp - 12],%g3
SET 8,%g3
		ST %g3,[%fp - 12]
		LD [%fp - 8],%g1
		LD [%fp - 12],%g2
		LD [%fp - 8],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 8]
		LD [%fp - 16],%g3
	SET 7,%g3
		ST %g3,[%fp - 16]
		LD [%fp - 16],%g1
		LD [%fp - 8],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 8]
		LD [%fp - 20],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 20]
		LD [%fp - 20],%g1
		LD [%fp - 24],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 24]
		LD [%fp - 28],%g3
SET 12,%g3
		ST %g3,[%fp - 28]
		LD [%fp - 24],%g1
		LD [%fp - 28],%g2
		LD [%fp - 24],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 24]
		LD [%fp - 32],%g3
	SET 12,%g3
		ST %g3,[%fp - 32]
		LD [%fp - 32],%g1
		LD [%fp - 24],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 24]
		LD [%fp - 36],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 36]
		LD [%fp - 36],%g1
		LD [%fp - 40],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 40]
		LD [%fp - 44],%g3
SET 16,%g3
		ST %g3,[%fp - 44]
		LD [%fp - 40],%g1
		LD [%fp - 44],%g2
		LD [%fp - 40],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 40]
		LD [%fp - 48],%g3
	SET 18,%g3
		ST %g3,[%fp - 48]
		LD [%fp - 48],%g1
		LD [%fp - 40],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 40]
		LD [%fp - 52],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 52]
		LD [%fp - 52],%g1
		LD [%fp - 56],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 56]
		LD [%fp - 60],%g3
SET 20,%g3
		ST %g3,[%fp - 60]
		LD [%fp - 56],%g1
		LD [%fp - 60],%g2
		LD [%fp - 56],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 56]
		LD [%fp - 64],%g3
	SET 2,%g3
		ST %g3,[%fp - 64]
		LD [%fp - 64],%g1
		LD [%fp - 56],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 56]
		LD [%fp - 68],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 68]
		LD [%fp - 68],%g1
		LD [%fp - 72],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 72]
		LD [%fp - 76],%g3
SET 24,%g3
		ST %g3,[%fp - 76]
		LD [%fp - 72],%g1
		LD [%fp - 76],%g2
		LD [%fp - 72],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 72]
		LD [%fp - 80],%g3
	SET 11,%g3
		ST %g3,[%fp - 80]
		LD [%fp - 80],%g1
		LD [%fp - 72],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 72]
		LD [%fp - 84],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 84]
		LD [%fp - 84],%g1
		LD [%fp - 88],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 88]
		LD [%fp - 92],%g3
SET 28,%g3
		ST %g3,[%fp - 92]
		LD [%fp - 88],%g1
		LD [%fp - 92],%g2
		LD [%fp - 88],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 88]
		LD [%fp - 96],%g3
	SET 6,%g3
		ST %g3,[%fp - 96]
		LD [%fp - 96],%g1
		LD [%fp - 88],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 88]
		LD [%fp - 100],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 100]
		LD [%fp - 100],%g1
		LD [%fp - 104],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 104]
		LD [%fp - 108],%g3
SET 32,%g3
		ST %g3,[%fp - 108]
		LD [%fp - 104],%g1
		LD [%fp - 108],%g2
		LD [%fp - 104],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 104]
		LD [%fp - 112],%g3
	SET 9,%g3
		ST %g3,[%fp - 112]
		LD [%fp - 112],%g1
		LD [%fp - 104],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 104]
		LD [%fp - 116],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 116]
		LD [%fp - 116],%g1
		LD [%fp - 120],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 120]
		LD [%fp - 124],%g3
SET 36,%g3
		ST %g3,[%fp - 124]
		LD [%fp - 120],%g1
		LD [%fp - 124],%g2
		LD [%fp - 120],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 120]
		LD [%fp - 128],%g3
	SET 19,%g3
		ST %g3,[%fp - 128]
		LD [%fp - 128],%g1
		LD [%fp - 120],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 120]
		LD [%fp - 132],%g3
	SUB %i0,4,%g3
		ST %g3,[%fp - 132]
		LD [%fp - 132],%g1
		LD [%fp - 136],%g3
	LD [%g1],%g3
		ST %g3,[%fp - 136]
		LD [%fp - 140],%g3
SET 40,%g3
		ST %g3,[%fp - 140]
		LD [%fp - 136],%g1
		LD [%fp - 140],%g2
		LD [%fp - 136],%g3
ADD %g1,%g2,%g3
		ST %g3,[%fp - 136]
		LD [%fp - 144],%g3
	SET 5,%g3
		ST %g3,[%fp - 144]
		LD [%fp - 144],%g1
		LD [%fp - 136],%g3
	ST %g1,[%g3]
		ST %g3,[%fp - 136]
	SET 0,%i0
	BA Init024FuncEnd
	NOP
Init024FuncEnd:
	ret
	restore
