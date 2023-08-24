import numpy as np
import matplotlib.pyplot as plt
import sounddevice as sd
from scipy.fftpack import fft

t = np.linspace(0, 3, 12288)

"""0 and 3 signify that the code will be run through 0 to 3 seconds of time
x-axis for t-domain"""

outputSignals = (
    (np.sin(2*np.pi*246.93*t) + np.sin(2*np.pi*261.63*t)) * np.where(np.logical_and(t >= 0, t < 0.3), 1, 0) +
    (np.sin(2*np.pi*246.93*t) + np.sin(2*np.pi*261.63*t)) * np.where(np.logical_and(t >= 0.3, t < 0.6), 1, 0) +
    (np.sin(2*np.pi*246.93*t) + np.sin(2*np.pi*261.63*t)) * np.where(np.logical_and(t >= 0.6, t < 0.9), 1, 0) +
    (np.sin(2*np.pi*196*t) + np.sin(2*np.pi*392*t)) * np.where(np.logical_and(t >= 0.9, t < 1.2), 1, 0) +
    (np.sin(2*np.pi*196*t) + np.sin(2*np.pi*392*t)) * np.where(np.logical_and(t >= 1.2, t < 1.5), 1, 0) +
    (np.sin(2*np.pi*196*t) + np.sin(2*np.pi*392*t)) * np.where(np.logical_and(t >= 1.5, t < 1.8), 1, 0) +
    (np.sin(2*np.pi*130.81*t) + np.sin(2*np.pi*293.66*t)) * np.where(np.logical_and(t >= 1.8, t < 2.1), 1, 0) +
    (np.sin(2*np.pi*130.81*t) + np.sin(2*np.pi*293.66*t)) * np.where(np.logical_and(t >= 2.1, t < 2.4), 1, 0) +
    (np.sin(2*np.pi*130.81*t) + np.sin(2*np.pi*293.66*t)) * np.where(np.logical_and(t >= 2.4, t < 2.7), 1, 0) +
    (np.sin(2*np.pi*130.81*t) + np.sin(2*np.pi*293.66*t)) * np.where(np.logical_and(t >= 2.7, t < 3), 1, 0)
)

"""outputSignal is the original untouched signal composed of the summation of a series of coupled frequencies 
from the 3rd and 4th octaves"""

"""Methodology of Summation: the np.where( ) method is used especially in the summation as it 
incorporates Logical AND which is a powerful tool in our project as we can control when a certain 
coupled frequency should be activated on the x-axis."""

N = 3 * 1024
f = np.linspace(0, 512, int(N/2))
x_f = fft(outputSignals)
x_f = 2/N * np.abs(x_f[0:np.int(N/2)])
fnoise1 = np.random.randint(0, 512)
fnoise2 = np.random.randint(0, 512)

"""We start by defining the f-domain"""

"""then we generate two random frequencies that are considered as noise: “fnoise1” & 
“fnoise2” """

noise = np.sin(2*fnoise1*np.pi*t) + np.sin(2*fnoise2*np.pi*t)

"""Then we stored the summation of these 2 frequencies in the “noise” variable. """

xn = noise + outputSignals

"""Next we combined the noise with our original output signal by addition in new variable “xn”
which basically stands for x-noised"""

xn_f = fft(xn)
xn_f = 2/N * np.abs(xn_f[0:np.int(N/2)])

"""We got their representations in the frequency domain (f-domain) and stored them in “x_f” & “xn_f”
respectively. 
"""

z = np.where(xn_f > np.ceil(np.max(outputSignals)))
fnoise1_new = int(f[z[0][0]])
fnoise2_new = int(f[z[0][1]]) 

"""(59) This line uses the np.where function to find the indices where the absolute values of the elements 
in the frequency domain signal xn_f are greater than the maximum amplitude of the original signal outputSignals
(obtained using np.max(outputSignals)). This identifies the frequencies in xn_f that have 
abnormally high amplitudes, which are likely to correspond to the added noise.
"""

"""(60) (61) These lines retrieve the corresponding frequencies for the two highest-amplitude components in the noise. 
The indices of these frequencies are obtained from z (which stores the indices identified in the 
previous step), and then the corresponding frequencies are extracted from the f array. Since f 
represents the frequency axis from 0 to 512 Hz (as determined by np.linspace(0, 512, 
int(N/2))), the frequencies are rounded and converted to integers.
"""

x_filtered = xn - (np.sin(2*fnoise1_new*np.pi*t) + np.sin(2*fnoise2_new*np.pi*t))
x_filtered_f = fft(x_filtered)
x_filtered_f = 2/N * np.abs(x_filtered_f[0:np.int(N/2)])

"""x_filtered is obtained by subtracting the sinusoidal noise signals with frequencies 
fnoise1_new and fnoise2_new from xn. 
"""
plt.figure(1)

plt.subplot(3, 2, 1)
plt.plot(t, outputSignals)
plt.title('Original Signal - Time Domain')

plt.subplot(3, 2, 2)
plt.plot(f, x_f)
plt.title('Original Signal - Frequency Domain')

plt.subplot(3, 2, 3)
plt.plot(t, xn)
plt.title('Signal with Noise - Time Domain')

plt.subplot(3, 2, 4)
plt.plot(f, xn_f)
plt.title('Signal with Noise - Frequency Domain')

plt.subplot(3, 2, 5)
plt.plot(t, x_filtered)
plt.title('Filtered Signal - Time Domain')

plt.subplot(3, 2, 6)
plt.plot(f, x_filtered_f)
plt.title('Filtered Signal - Frequency Domain')

plt.tight_layout()

"""sd.play(outputSignals, 4*1024)"""
"""sd.play(xn, 4*1024)"""
sd.play(x_filtered, 4 * 1024)
plt.show()

"""This setting allows for the output of the filtered signal, Play around with them to hear the difference"""
"""outputSignal: Original Signal, xn: Original+noise, x_filtered: Signal with noise-cancellation"""