/*
    Copyright (C) 2014  sarin

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.otus.gost;

/**
 *
 * @author sarin
 */
abstract class StribogB extends StribogAlg {

    private final int[] m = new int[64];
    private int pos = 63;

    private int[] h = new int[64];
    private int[] N = new int[64];
    private int[] Sigma = new int[64];
    private final int[] IV;

    protected StribogB(int[] IV) {
        this.IV = IV;
        System.arraycopy(IV, 0, h, 0, 64);
    }

    @Override
    protected void engineUpdate(byte input) {
        m[pos] = input & 0xFF;
        pos--;
        if (pos == -1) {
            h = gN(N, h, m);
            N = add(N, Data.bv512);
            Sigma = add(Sigma, m);
            pos = 63;
        }
    }

    @Override
    protected void engineReset() {
        System.arraycopy(IV, 0, h, 0, 64);        
        pos = 63;
        N = new int[64];
        Sigma = new int[64];
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        for (int i = offset; i < len; i++) {
            engineUpdate(input[i]);
        }
    }

    @Override
    protected byte[] engineDigest() {
        for (int i = 0; i < pos; i++) {
            m[i] = 0;
        }
        
        m[pos] = 0x01;
        h = gN(N, h, m);
        int[] bv = new int[64];
        bv[62] = ((63 - pos) * 8) >> 8;
        bv[63] = ((63 - pos) * 8) & 0xFF;
        N = add(N, bv);
        Sigma = add(Sigma, m);
        h = gN(Data.bv00, h, N);
        h = gN(Data.bv00, h, Sigma);
        byte[] result = new byte[h.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) h[i];
        }
        return result;
    }

}
