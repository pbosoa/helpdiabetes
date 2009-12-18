/*  
 *  Copyright (C) 2009  Johan Degraeve
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/gpl.txt>.
 *    
 *  Please contact Johan Degraeve at johan.degraeve@johandegraeve.net if you need
 *  additional information or have any questions.
 */
package net.johandegraeve.helpdiabetes;

/**
 * This class defines the static method compareToAsInExcel, which sorts characters in the same
 * way as Excel does.<p>
 *  The following table lists the characters with Ascii code 1 to 255, ordered in the way Excel does it. 
 *  (some characters may not be represented correctly  - depending on the browser). An exception is 
 *  the character with ASCII code 44 which is considered as the 'smallest character', because it 
 *  will never be shown in a list, should not appear in text, and only appears in byte range as 
 *  delimiter between the different fields.
 *  <p>
 *  The result of this comparison is that for example following characters are considered equal : 
 *  &#69;,&#101;,&#201;,&#233;,&#200;,&#232;,&#202;,&#234;,&#203;,&#235;,<p> 
 *  <table border="1">
 *  	<tr>
 *  		<th>Ascii code</th>
 *  		<th>Character</th>
 *  		<th>Considered equal to</th>
 *  	</tr>
 *                    <tr><th>1</th><th>&#1;</th><th>1</th></tr>
 *                    <tr><th>2</th><th>&#2;</th><th>2</th></tr>
 *                    <tr><th>3</th><th>&#3;</th><th>3</th></tr>
 *                    <tr><th>4</th><th>&#4;</th><th>4</th></tr>
 *                    <tr><th>5</th><th>&#5;</th><th>5</th></tr>
 *                    <tr><th>6</th><th>&#6;</th><th>6</th></tr>
 *                    <tr><th>7</th><th>&#7;</th><th>7</th></tr>
 *                    <tr><th>8</th><th>&#8;</th><th>8</th></tr>
 *                    <tr><th>14</th><th>&#14;</th><th>9</th></tr>
 *                    <tr><th>15</th><th>&#15;</th><th>10</th></tr>
 *                    <tr><th>16</th><th>&#16;</th><th>11</th></tr>
 *                    <tr><th>17</th><th>&#17;</th><th>12</th></tr>
 *                    <tr><th>18</th><th>&#18;</th><th>13</th></tr>
 *                    <tr><th>19</th><th>&#19;</th><th>14</th></tr>
 *                    <tr><th>20</th><th>&#20;</th><th>15</th></tr>
 *                    <tr><th>21</th><th>&#21;</th><th>16</th></tr>
 *                    <tr><th>22</th><th>&#22;</th><th>17</th></tr>
 *                    <tr><th>23</th><th>&#23;</th><th>18</th></tr>
 *                    <tr><th>24</th><th>&#24;</th><th>19</th></tr>
 *                    <tr><th>25</th><th>&#25;</th><th>20</th></tr>
 *                    <tr><th>26</th><th>&#26;</th><th>21</th></tr>
 *                    <tr><th>27</th><th>&#27;</th><th>22</th></tr>
 *                    <tr><th>28</th><th>&#28;</th><th>23</th></tr>
 *                    <tr><th>29</th><th>&#29;</th><th>24</th></tr>
 *                    <tr><th>30</th><th>&#30;</th><th>25</th></tr>
 *                    <tr><th>31</th><th>&#31;</th><th>26</th></tr>
 *                    <tr><th>127</th><th>&#127;</th><th>27</th></tr>
 *                    <tr><th>129</th><th>&#129;</th><th>28</th></tr>
 *                    <tr><th>141</th><th>&#141;</th><th>29</th></tr>
 *                    <tr><th>143</th><th>&#143;</th><th>30</th></tr>
 *                    <tr><th>144</th><th>&#144;</th><th>31</th></tr>
 *                    <tr><th>157</th><th>&#157;</th><th>32</th></tr>
 *                    <tr><th>39</th><th>&#39;</th><th>33</th></tr>
 *                    <tr><th>45</th><th>&#45;</th><th>34</th></tr>
 *                    <tr><th>173</th><th>&#173;</th><th>35</th></tr>
 *                    <tr><th>150</th><th>&#150;</th><th>36</th></tr>
 *                    <tr><th>151</th><th>&#151;</th><th>37</th></tr>
 *                    <tr><th>32</th><th>&#32;</th><th>38</th></tr>
 *                    <tr><th>160</th><th>&#160;</th><th>39</th></tr>
 *                    <tr><th>9</th><th>&#9;</th><th>40</th></tr>
 *                    <tr><th>10</th><th>&#10;</th><th>41</th></tr>
 *                    <tr><th>11</th><th>&#11;</th><th>42</th></tr>
 *                    <tr><th>12</th><th>&#12;</th><th>43</th></tr>
 *                    <tr><th>13</th><th>&#13;</th><th>44</th></tr>
 *                    <tr><th>33</th><th>&#33;</th><th>45</th></tr>
 *                    <tr><th>34</th><th>&#34;</th><th>46</th></tr>
 *                    <tr><th>35</th><th>&#35;</th><th>47</th></tr>
 *                    <tr><th>36</th><th>&#36;</th><th>48</th></tr>
 *                    <tr><th>37</th><th>&#37;</th><th>49</th></tr>
 *                    <tr><th>38</th><th>&#38;</th><th>50</th></tr>
 *                    <tr><th>40</th><th>&#40;</th><th>51</th></tr>
 *                    <tr><th>41</th><th>&#41;</th><th>52</th></tr>
 *                    <tr><th>42</th><th>&#42;</th><th>53</th></tr>
 *                    <tr><th>44</th><th>&#44;</th><th>54</th></tr>
 *                    <tr><th>46</th><th>&#46;</th><th>55</th></tr>
 *                    <tr><th>47</th><th>&#47;</th><th>56</th></tr>
 *                    <tr><th>58</th><th>&#58;</th><th>57</th></tr>
 *                    <tr><th>59</th><th>&#59;</th><th>58</th></tr>
 *                    <tr><th>63</th><th>&#63;</th><th>59</th></tr>
 *                    <tr><th>64</th><th>&#64;</th><th>60</th></tr>
 *                    <tr><th>91</th><th>&#91;</th><th>61</th></tr>
 *                    <tr><th>92</th><th>&#92;</th><th>62</th></tr>
 *                    <tr><th>93</th><th>&#93;</th><th>63</th></tr>
 *                    <tr><th>94</th><th>&#94;</th><th>64</th></tr>
 *                    <tr><th>136</th><th>&#136;</th><th>65</th></tr>
 *                    <tr><th>95</th><th>&#95;</th><th>66</th></tr>
 *                    <tr><th>96</th><th>&#96;</th><th>67</th></tr>
 *                    <tr><th>123</th><th>&#123;</th><th>68</th></tr>
 *                    <tr><th>124</th><th>&#124;</th><th>69</th></tr>
 *                    <tr><th>125</th><th>&#125;</th><th>70</th></tr>
 *                    <tr><th>126</th><th>&#126;</th><th>71</th></tr>
 *                    <tr><th>161</th><th>&#161;</th><th>72</th></tr>
 *                    <tr><th>166</th><th>&#166;</th><th>73</th></tr>
 *                    <tr><th>168</th><th>&#168;</th><th>74</th></tr>
 *                    <tr><th>175</th><th>&#175;</th><th>75</th></tr>
 *                    <tr><th>180</th><th>&#180;</th><th>76</th></tr>
 *                    <tr><th>184</th><th>&#184;</th><th>77</th></tr>
 *                    <tr><th>191</th><th>&#191;</th><th>78</th></tr>
 *                    <tr><th>152</th><th>&#152;</th><th>79</th></tr>
 *                    <tr><th>145</th><th>&#145;</th><th>80</th></tr>
 *                    <tr><th>146</th><th>&#146;</th><th>81</th></tr>
 *                    <tr><th>130</th><th>&#130;</th><th>82</th></tr>
 *                    <tr><th>147</th><th>&#147;</th><th>83</th></tr>
 *                    <tr><th>148</th><th>&#148;</th><th>84</th></tr>
 *                    <tr><th>132</th><th>&#132;</th><th>85</th></tr>
 *                    <tr><th>139</th><th>&#139;</th><th>86</th></tr>
 *                    <tr><th>155</th><th>&#155;</th><th>87</th></tr>
 *                    <tr><th>43</th><th>&#43;</th><th>88</th></tr>
 *                    <tr><th>60</th><th>&#60;</th><th>89</th></tr>
 *                    <tr><th>61</th><th>&#61;</th><th>90</th></tr>
 *                    <tr><th>62</th><th>&#62;</th><th>91</th></tr>
 *                    <tr><th>177</th><th>&#177;</th><th>92</th></tr>
 *                    <tr><th>171</th><th>&#171;</th><th>93</th></tr>
 *                    <tr><th>187</th><th>&#187;</th><th>94</th></tr>
 *                    <tr><th>215</th><th>&#215;</th><th>95</th></tr>
 *                    <tr><th>247</th><th>&#247;</th><th>96</th></tr>
 *                    <tr><th>162</th><th>&#162;</th><th>97</th></tr>
 *                    <tr><th>163</th><th>&#163;</th><th>98</th></tr>
 *                    <tr><th>164</th><th>&#164;</th><th>99</th></tr>
 *                    <tr><th>165</th><th>&#165;</th><th>100</th></tr>
 *                    <tr><th>167</th><th>&#167;</th><th>101</th></tr>
 *                    <tr><th>169</th><th>&#169;</th><th>102</th></tr>
 *                    <tr><th>172</th><th>&#172;</th><th>103</th></tr>
 *                    <tr><th>174</th><th>&#174;</th><th>104</th></tr>
 *                    <tr><th>176</th><th>&#176;</th><th>105</th></tr>
 *                    <tr><th>181</th><th>&#181;</th><th>106</th></tr>
 *                    <tr><th>182</th><th>&#182;</th><th>107</th></tr>
 *                    <tr><th>183</th><th>&#183;</th><th>108</th></tr>
 *                    <tr><th>134</th><th>&#134;</th><th>109</th></tr>
 *                    <tr><th>135</th><th>&#135;</th><th>110</th></tr>
 *                    <tr><th>149</th><th>&#149;</th><th>111</th></tr>
 *                    <tr><th>133</th><th>&#133;</th><th>112</th></tr>
 *                    <tr><th>137</th><th>&#137;</th><th>113</th></tr>
 *                    <tr><th>128</th><th>&#128;</th><th>114</th></tr>
 *                    <tr><th>48</th><th>&#48;</th><th>115</th></tr>
 *                    <tr><th>188</th><th>&#188;</th><th>116</th></tr>
 *                    <tr><th>189</th><th>&#189;</th><th>117</th></tr>
 *                    <tr><th>190</th><th>&#190;</th><th>118</th></tr>
 *                    <tr><th>49</th><th>&#49;</th><th>120</th></tr>
 *                    <tr><th>185</th><th>&#185;</th><th>120</th></tr>
 *                    <tr><th>50</th><th>&#50;</th><th>122</th></tr>
 *                    <tr><th>178</th><th>&#178;</th><th>122</th></tr>
 *                    <tr><th>51</th><th>&#51;</th><th>124</th></tr>
 *                    <tr><th>179</th><th>&#179;</th><th>124</th></tr>
 *                    <tr><th>52</th><th>&#52;</th><th>125</th></tr>
 *                    <tr><th>53</th><th>&#53;</th><th>126</th></tr>
 *                    <tr><th>54</th><th>&#54;</th><th>127</th></tr>
 *                    <tr><th>55</th><th>&#55;</th><th>128</th></tr>
 *                    <tr><th>56</th><th>&#56;</th><th>129</th></tr>
 *                    <tr><th>57</th><th>&#57;</th><th>130</th></tr>
 *                    <tr><th>65</th><th>&#65;</th><th>147</th></tr>
 *                    <tr><th>97</th><th>&#97;</th><th>147</th></tr>
 *                    <tr><th>170</th><th>&#170;</th><th>147</th></tr>
 *                    <tr><th>193</th><th>&#193;</th><th>147</th></tr>
 *                    <tr><th>225</th><th>&#225;</th><th>147</th></tr>
 *                    <tr><th>192</th><th>&#192;</th><th>147</th></tr>
 *                    <tr><th>224</th><th>&#224;</th><th>147</th></tr>
 *                    <tr><th>194</th><th>&#194;</th><th>147</th></tr>
 *                    <tr><th>226</th><th>&#226;</th><th>147</th></tr>
 *                    <tr><th>196</th><th>&#196;</th><th>147</th></tr>
 *                    <tr><th>228</th><th>&#228;</th><th>147</th></tr>
 *                    <tr><th>195</th><th>&#195;</th><th>147</th></tr>
 *                    <tr><th>227</th><th>&#227;</th><th>147</th></tr>
 *                    <tr><th>197</th><th>&#197;</th><th>147</th></tr>
 *                    <tr><th>229</th><th>&#229;</th><th>147</th></tr>
 *                    <tr><th>198</th><th>&#198;</th><th>147</th></tr>
 *                    <tr><th>230</th><th>&#230;</th><th>147</th></tr>
 *                    <tr><th>66</th><th>&#66;</th><th>149</th></tr>
 *                    <tr><th>98</th><th>&#98;</th><th>149</th></tr>
 *                    <tr><th>67</th><th>&#67;</th><th>153</th></tr>
 *                    <tr><th>99</th><th>&#99;</th><th>153</th></tr>
 *                    <tr><th>199</th><th>&#199;</th><th>153</th></tr>
 *                    <tr><th>231</th><th>&#231;</th><th>153</th></tr>
 *                    <tr><th>68</th><th>&#68;</th><th>157</th></tr>
 *                    <tr><th>100</th><th>&#100;</th><th>157</th></tr>
 *                    <tr><th>208</th><th>&#208;</th><th>157</th></tr>
 *                    <tr><th>240</th><th>&#240;</th><th>157</th></tr>
 *                    <tr><th>69</th><th>&#69;</th><th>167</th></tr>
 *                    <tr><th>101</th><th>&#101;</th><th>167</th></tr>
 *                    <tr><th>201</th><th>&#201;</th><th>167</th></tr>
 *                    <tr><th>233</th><th>&#233;</th><th>167</th></tr>
 *                    <tr><th>200</th><th>&#200;</th><th>167</th></tr>
 *                    <tr><th>232</th><th>&#232;</th><th>167</th></tr>
 *                    <tr><th>202</th><th>&#202;</th><th>167</th></tr>
 *                    <tr><th>234</th><th>&#234;</th><th>167</th></tr>
 *                    <tr><th>203</th><th>&#203;</th><th>167</th></tr>
 *                    <tr><th>235</th><th>&#235;</th><th>167</th></tr>
 *                    <tr><th>70</th><th>&#70;</th><th>170</th></tr>
 *                    <tr><th>102</th><th>&#102;</th><th>170</th></tr>
 *                    <tr><th>131</th><th>&#131;</th><th>170</th></tr>
 *                    <tr><th>71</th><th>&#71;</th><th>172</th></tr>
 *                    <tr><th>103</th><th>&#103;</th><th>172</th></tr>
 *                    <tr><th>72</th><th>&#72;</th><th>174</th></tr>
 *                    <tr><th>104</th><th>&#104;</th><th>174</th></tr>
 *                    <tr><th>73</th><th>&#73;</th><th>184</th></tr>
 *                    <tr><th>105</th><th>&#105;</th><th>184</th></tr>
 *                    <tr><th>205</th><th>&#205;</th><th>184</th></tr>
 *                    <tr><th>237</th><th>&#237;</th><th>184</th></tr>
 *                    <tr><th>204</th><th>&#204;</th><th>184</th></tr>
 *                    <tr><th>236</th><th>&#236;</th><th>184</th></tr>
 *                    <tr><th>206</th><th>&#206;</th><th>184</th></tr>
 *                    <tr><th>238</th><th>&#238;</th><th>184</th></tr>
 *                    <tr><th>207</th><th>&#207;</th><th>184</th></tr>
 *                    <tr><th>239</th><th>&#239;</th><th>184</th></tr>
 *                    <tr><th>74</th><th>&#74;</th><th>186</th></tr>
 *                    <tr><th>106</th><th>&#106;</th><th>186</th></tr>
 *                    <tr><th>75</th><th>&#75;</th><th>188</th></tr>
 *                    <tr><th>107</th><th>&#107;</th><th>188</th></tr>
 *                    <tr><th>76</th><th>&#76;</th><th>190</th></tr>
 *                    <tr><th>108</th><th>&#108;</th><th>190</th></tr>
 *                    <tr><th>77</th><th>&#77;</th><th>192</th></tr>
 *                    <tr><th>109</th><th>&#109;</th><th>192</th></tr>
 *                    <tr><th>78</th><th>&#78;</th><th>196</th></tr>
 *                    <tr><th>110</th><th>&#110;</th><th>196</th></tr>
 *                    <tr><th>209</th><th>&#209;</th><th>196</th></tr>
 *                    <tr><th>241</th><th>&#241;</th><th>196</th></tr>
 *                    <tr><th>79</th><th>&#79;</th><th>213</th></tr>
 *                    <tr><th>111</th><th>&#111;</th><th>213</th></tr>
 *                    <tr><th>186</th><th>&#186;</th><th>213</th></tr>
 *                    <tr><th>211</th><th>&#211;</th><th>213</th></tr>
 *                    <tr><th>243</th><th>&#243;</th><th>213</th></tr>
 *                    <tr><th>210</th><th>&#210;</th><th>213</th></tr>
 *                    <tr><th>242</th><th>&#242;</th><th>213</th></tr>
 *                    <tr><th>212</th><th>&#212;</th><th>213</th></tr>
 *                    <tr><th>244</th><th>&#244;</th><th>213</th></tr>
 *                    <tr><th>214</th><th>&#214;</th><th>213</th></tr>
 *                    <tr><th>246</th><th>&#246;</th><th>213</th></tr>
 *                    <tr><th>213</th><th>&#213;</th><th>213</th></tr>
 *                    <tr><th>245</th><th>&#245;</th><th>213</th></tr>
 *                    <tr><th>216</th><th>&#216;</th><th>213</th></tr>
 *                    <tr><th>248</th><th>&#248;</th><th>213</th></tr>
 *                    <tr><th>140</th><th>&#140;</th><th>213</th></tr>
 *                    <tr><th>156</th><th>&#156;</th><th>213</th></tr>
 *                    <tr><th>80</th><th>&#80;</th><th>215</th></tr>
 *                    <tr><th>112</th><th>&#112;</th><th>215</th></tr>
 *                    <tr><th>81</th><th>&#81;</th><th>217</th></tr>
 *                    <tr><th>113</th><th>&#113;</th><th>217</th></tr>
 *                    <tr><th>82</th><th>&#82;</th><th>219</th></tr>
 *                    <tr><th>114</th><th>&#114;</th><th>219</th></tr>
 *                    <tr><th>83</th><th>&#83;</th><th>224</th></tr>
 *                    <tr><th>115</th><th>&#115;</th><th>224</th></tr>
 *                    <tr><th>138</th><th>&#138;</th><th>224</th></tr>
 *                    <tr><th>154</th><th>&#154;</th><th>224</th></tr>
 *                    <tr><th>223</th><th>&#223;</th><th>224</th></tr>
 *                    <tr><th>84</th><th>&#84;</th><th>229</th></tr>
 *                    <tr><th>116</th><th>&#116;</th><th>229</th></tr>
 *                    <tr><th>222</th><th>&#222;</th><th>229</th></tr>
 *                    <tr><th>254</th><th>&#254;</th><th>229</th></tr>
 *                    <tr><th>153</th><th>&#153;</th><th>229</th></tr>
 *                    <tr><th>85</th><th>&#85;</th><th>239</th></tr>
 *                    <tr><th>117</th><th>&#117;</th><th>239</th></tr>
 *                    <tr><th>218</th><th>&#218;</th><th>239</th></tr>
 *                    <tr><th>250</th><th>&#250;</th><th>239</th></tr>
 *                    <tr><th>217</th><th>&#217;</th><th>239</th></tr>
 *                    <tr><th>249</th><th>&#249;</th><th>239</th></tr>
 *                    <tr><th>219</th><th>&#219;</th><th>239</th></tr>
 *                    <tr><th>251</th><th>&#251;</th><th>239</th></tr>
 *                    <tr><th>220</th><th>&#220;</th><th>239</th></tr>
 *                    <tr><th>252</th><th>&#252;</th><th>239</th></tr>
 *                    <tr><th>86</th><th>&#86;</th><th>241</th></tr>
 *                    <tr><th>118</th><th>&#118;</th><th>241</th></tr>
 *                    <tr><th>87</th><th>&#87;</th><th>243</th></tr>
 *                    <tr><th>119</th><th>&#119;</th><th>243</th></tr>
 *                    <tr><th>88</th><th>&#88;</th><th>245</th></tr>
 *                    <tr><th>120</th><th>&#120;</th><th>245</th></tr>
 *                    <tr><th>89</th><th>&#89;</th><th>251</th></tr>
 *                    <tr><th>121</th><th>&#121;</th><th>251</th></tr>
 *                    <tr><th>221</th><th>&#221;</th><th>251</th></tr>
 *                    <tr><th>253</th><th>&#253;</th><th>251</th></tr>
 *                    <tr><th>159</th><th>&#159;</th><th>251</th></tr>
 *                    <tr><th>255</th><th>&#255;</th><th>251</th></tr>
 *                    <tr><th>90</th><th>&#90;</th><th>255</th></tr>
 *                    <tr><th>122</th><th>&#122;</th><th>255</th></tr>
 *                    <tr><th>142</th><th>&#142;</th><th>255</th></tr>
 *                    <tr><th>158</th><th>&#158;</th><th>255</th></tr>
 *         </table>           
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class ExcelCharacter   {
    
    /**
     * Array used for comparing char values according to Excel rules.
     * Element at index x, has the same value as in column 'Considered Equal to' in the table above, 
     * for the row with Ascii code x.<br>
     * The last element has value '256', this is the value that will be used for any character which char-value out of range 
     */
    static private final char[] CHARORDER = {  0,   1,  2,  3,  4,  5,  6,  7,  8,  40,
        41,  42, 43, 44, 9,  10, 11, 12, 13, 14,
        15,  16, 17, 18, 19, 20, 21, 22, 23, 24,
        25,  26, 38, 45, 46, 47, 48, 49, 50, 33,
        51,  52, 53, 88,  0, 34, 55, 56,115,120,//44 which is ascii code for , is being mapped to 0, this is because , will never be shown in list, should not appear in text, and only appears in byte range as delimiter between the different fields
        122,124,125,126,127,128,129,130, 57, 58,
        89,  90, 91, 59, 60,147,149,153,157,167,
        170,172,174,184,186,188,190,192,196,213,
        215,217,219,224,229,239,241,243,245,251,
        255, 61, 62, 63, 64, 66, 67,147,149,153,
        157,167,170,172,174,184,186,188,190,192,
        196,213,215,217,219,224,229,239,241,243,
        245,251,255, 68, 69, 70, 71, 27,114, 28,
        82 ,170, 85,112,109,110, 65,113,224, 86,
        213, 29,255, 30, 31, 80, 81, 83, 84,111,
        36,  37, 79,229,224, 87,213, 32,255,251,
        39,  72, 97, 98, 99,100, 73,101, 74,102,
        147, 93,103, 35,104, 75,105, 92,122,124,
        76, 106,107,108, 77,120,213, 94,116,117,
        118, 78,147,147,147,147,147,147,147,153,
        167,167,167,167,184,184,184,184,157,196,
        213,213,213,213,213, 95,213,239,239,239,
        239,251,229,224,147,147,147,147,147,147,
        147,153,167,167,167,167,184,184,184,184,
        157,196,213,213,213,213,213, 96,213,239,
        239,239,239,251,229,251,256};
    
    
    /**
     * Compares two characters using Excel sorting rules.
     * @param characterA the first character to be compared to  
     * @param characterB the second character
     * @return the value 0 if characterA is equal to characterB; 
     * 	a value less than 0 if characterA is numerically less than characterB; 
     * 	and a value greater than 0 if characterB is numerically greater than characterA. 
     * 	Note that this is a comparison of the value in column 3 (considered equal to) in the table above.
     * 
     */
    static public int compareToAsInExcel(char characterA, char characterB) {
	if (characterA > (CHARORDER.length - 1))//-1 because the very last element is not really considered to be a character value 
	    characterA = CHARORDER[CHARORDER.length - 1];
	if (characterB > (CHARORDER.length - 1))//-1 because the very last element is not really considered to be a character value 
	    characterB = CHARORDER[CHARORDER.length - 1];
	
	if (CHARORDER[characterA] < CHARORDER[characterB]) return -1;
	if (CHARORDER[characterA] > CHARORDER[characterB]) return 1;
	return 0;
    }
}
